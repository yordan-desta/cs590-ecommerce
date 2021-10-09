package edu.miu.cs590.sa.ecommerce.PaypalPaymentService.controller;

import edu.miu.cs590.sa.ecommerce.PaypalPaymentService.controller.models.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/paypal")
public class PaypalPaymentController {


    @PostMapping
    public String processPayment(@RequestBody PaymentRequest request){
        log.info("processed paypal payment for :" + request.toString());

        return UUID.randomUUID().toString();
    }
}
