package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.*;
import edu.miu.cs590.sa.ecommerce.dto.OrderDTO;
import edu.miu.cs590.sa.ecommerce.dto.ProductDTO;
import edu.miu.cs590.sa.ecommerce.repository.OrderRepository;
import edu.miu.cs590.sa.ecommerce.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private final PaymentService paymentService;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, RestTemplate restTemplate, PaymentService paymentService, ProductService productService) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.paymentService = paymentService;
        this.productService = productService;
    }

    @Override
    public List<OrderDTO> getAll() {
        return MapperUtil.mapList(repository.findAll(), OrderDTO.class);
    }

    @Override
    public OrderDTO get(Long id) {
        return MapperUtil.map(repository.getById(id), OrderDTO.class);
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = MapperUtil.map(orderDTO, Order.class);
        order.setStatus(OrderStatus.DRAFT);
        for (Product p : order.getProducts()) {
            ProductDTO product = restTemplate.getForObject(productService.getProductUri()+p.getProductId(), ProductDTO.class);
            if(product.getQuantity() < p.getQuantity()) {
                log.info("Product [" + p.getProductId() + "] stock is below the requested quantity.");
                return null;
            }
        }
        productService.saveAll(order.getProducts());
        return MapperUtil.map(repository.save(order), OrderDTO.class);
    }

    @Override
    public OrderDTO updateStatus(Long id, OrderStatus status) {
        Order order = repository.getById(id);
        order.setStatus(status);
        OrderDTO orderDTO = MapperUtil.map(repository.save(order), OrderDTO.class);
        log.info("Order " + status.toString() + "to: " + order.getShippingAddress());
        return orderDTO;
    }

    @Override
    public OrderDTO pay(Long id, PaymentInfo paymentInfo) {
        Order order = repository.getById(id);
        PaymentRequest request = new PaymentRequest();
        request.setUserId(order.getUserId());
        request.setOrderId(id.toString());
        request.setPaypalId(paymentInfo.getPaypalId());
        request.setCreditCardNumber(paymentInfo.getCreditCardNumber());
        Double totalPrice = 0.0;
        for (Product p : order.getProducts()) {
            ProductDTO product = restTemplate.getForObject(productService.getProductUri()+p.getProductId(), ProductDTO.class);
            totalPrice += product.getPrice() * p.getQuantity();
        }
        request.setBalance(totalPrice);

        String paymentUri = order.getPaymentType().equals(PaymentType.PAYPAL) ?
                paymentService.getPaypalPaymentUri(): paymentService.getCcPaymentUri();
        log.info("sending request to: " + paymentUri);
        ResponseEntity<String> result = restTemplate.postForEntity(paymentUri, request, String.class);

        if(result.getStatusCode() != HttpStatus.OK){
            log.error("Payment failed!");
            return null;
        }
        for (Product p : order.getProducts()) {
            HttpStatus response = restTemplate.postForEntity(productService.getProductUri() + p.getProductId() + "/deduct/" + p.getQuantity(),
                    null, String.class).getStatusCode();

            if(response.equals(HttpStatus.BAD_REQUEST)) {
                log.info("Product [" + p.getProductId() + "] stock is below the requested quantity.");
                return null;
            }

        }
        order.setStatus(OrderStatus.PLACED);
        repository.save(order);
        log.info("Order payment successfully completed");
        return MapperUtil.map(repository.save(order), OrderDTO.class);
    }
}
