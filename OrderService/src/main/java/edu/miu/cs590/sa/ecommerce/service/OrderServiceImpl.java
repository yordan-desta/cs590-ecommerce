package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.*;
import edu.miu.cs590.sa.ecommerce.dto.OrderDTO;
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

    @Autowired
    public OrderServiceImpl(OrderRepository repository, RestTemplate restTemplate, PaymentService paymentService) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.paymentService = paymentService;
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
        //TODO validate quantity
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
        //TODO calculate balance
        request.setBalance(10.0);

        String paymentUri = order.getPaymentType().equals(PaymentType.PAYPAL) ?
                paymentService.getPaypalPaymentUri(): paymentService.getCcPaymentUri();
        log.info("sending request to: " + paymentUri);
        ResponseEntity<String> result = restTemplate.postForEntity(paymentUri, request, String.class);

        if(result.getStatusCode() != HttpStatus.OK){
            log.error("Payment failed!");
            return null;
        }
        //TODO update products quantity
        order.setStatus(OrderStatus.PLACED);
        repository.save(order);
        log.info("Order payment successfully completed");
        return MapperUtil.map(repository.save(order), OrderDTO.class);
    }
}
