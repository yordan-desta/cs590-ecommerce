package edu.miu.cs590.sa.ecommercce.PaymentService.service;

import edu.miu.cs590.sa.ecommercce.PaymentService.domain.OrderPayment;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentMethod;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentStatus;
import edu.miu.cs590.sa.ecommercce.PaymentService.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{

    private final RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(RestTemplate restTemplate, PaymentRepository paymentRepository) {
        this.restTemplate = restTemplate;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentStatus makePayment(PaymentMethod paymentMethod, String orderId) {
        log.info("sending request to: " + paymentMethod.getPaymentUri());
        ResponseEntity<String> result = restTemplate.postForEntity(
                paymentMethod.getPaymentUri(), orderId, String.class
        );
        if(result.getStatusCode() != HttpStatus.OK){
            //wrap into some responses message
            log.error("error has occurred!");
            return PaymentStatus.ERROR;
        }
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setPaymentType(paymentMethod.getType());
        orderPayment.setOrderId(orderId);
        orderPayment.setStatus(PaymentStatus.SUCCESS);
        orderPayment.setTransactionId(result.getBody());

        paymentRepository.save(orderPayment);

        log.info("Payment Processed and saved!");

        return PaymentStatus.SUCCESS;
    }
}
