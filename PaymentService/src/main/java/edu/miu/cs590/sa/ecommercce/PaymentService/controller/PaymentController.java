package edu.miu.cs590.sa.ecommercce.PaymentService.controller;

import edu.miu.cs590.sa.ecommercce.PaymentService.controller.models.CCPaymentRequest;
import edu.miu.cs590.sa.ecommercce.PaymentService.controller.models.PaypalPaymentRequest;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.*;
import edu.miu.cs590.sa.ecommercce.PaymentService.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final String paypalAddress;
    private final String ccAddress;


    @Autowired
    public PaymentController(PaymentService paymentService, @Value("${payment.url.paypal}") String paypalAddress, @Value("${payment.url.cc}") String ccAddress) {
        this.paymentService = paymentService;
        this.paypalAddress = paypalAddress;
        this.ccAddress = ccAddress;
    }

    @PostMapping("/paypal")
    public PaymentStatus processPaypal(@RequestBody PaypalPaymentRequest paymentRequest){

        log.info("making payment for " + paymentRequest);

        PaypalPayment payment = new PaypalPayment();
        payment.setUserId(paymentRequest.getUserId());
        payment.setPaypalId(paymentRequest.getPaypalId());
        payment.setBalance(paymentRequest.getBalance());

        PaypalPaymentMethod paymentMethod = new PaypalPaymentMethod(paypalAddress, payment);

        return paymentService.makePayment(paymentMethod, paymentRequest.getOrderId());
    }

    @PostMapping("/cc")
    public PaymentStatus processCC(@RequestBody CCPaymentRequest paymentRequest){

        log.info("making payment for " + paymentRequest);

        CCPayment payment = new CCPayment();
        payment.setUserId(paymentRequest.getUserId());
        payment.setCreditCardNumber(paymentRequest.getCreditCardNumber());
        payment.setBalance(paymentRequest.getBalance());
        CCPaymentMethod paymentMethod = new CCPaymentMethod(ccAddress, payment);


        return paymentService.makePayment(paymentMethod, paymentRequest.getOrderId());

    }
}
