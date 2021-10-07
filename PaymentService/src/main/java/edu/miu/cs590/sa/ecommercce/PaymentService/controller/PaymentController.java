package edu.miu.cs590.sa.ecommercce.PaymentService.controller;

import edu.miu.cs590.sa.ecommercce.PaymentService.controller.models.PaymentRequest;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.CCPaymentMethod;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentStatus;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentTypes;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaypalPaymentMethod;
import edu.miu.cs590.sa.ecommercce.PaymentService.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public PaymentStatus makePayment(@RequestBody PaymentRequest paymentRequest){
        PaymentTypes paymentType = paymentRequest.getPaymentType();

        log.info("making payment for " + paymentType.name());

        if(PaymentTypes.PAYPAL.equals(paymentType)){
            return paymentService.makePayment(new PaypalPaymentMethod(), paymentRequest.getOrderId());
        }

        if(PaymentTypes.CC.equals(paymentType)){
            return paymentService.makePayment(new CCPaymentMethod(), paymentRequest.getOrderId());
        }

        return PaymentStatus.UNSUPPORTED;
    }
}
