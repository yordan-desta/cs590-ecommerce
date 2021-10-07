package edu.miu.cs590.sa.ecommercce.PaymentService.service;

import edu.miu.cs590.sa.ecommercce.PaymentService.domain.*;
import edu.miu.cs590.sa.ecommercce.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        ResponseEntity<String> result = restTemplate.postForEntity(paymentMethod.getPaymentUri(), orderId, String.class);
        if(result.getStatusCode() != HttpStatus.OK){
            //wrap into some responses message
            return PaymentStatus.ERROR;
        }
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setPaymentType(paymentMethod.getType());
        orderPayment.setOrderId(orderId);
        orderPayment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(orderPayment);

        return PaymentStatus.SUCCESS;
    }
}
