package edu.miu.cs590.sa.ecommercce.PaymentService.controller;

import edu.miu.cs590.sa.ecommercce.PaymentService.controller.models.PaymentRequest;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.CCPaymentMethod;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentStatus;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentTypes;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaypalPaymentMethod;
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

    @PostMapping
    public PaymentStatus makePayment(@RequestBody PaymentRequest paymentRequest){

        log.info("making payment for " + paymentRequest);

        if(PaymentTypes.PAYPAL.name().equals(paymentRequest.getPaymentType())){
            return paymentService.makePayment(new PaypalPaymentMethod(paypalAddress), paymentRequest.getOrderId());
        }

        if(PaymentTypes.CC.name().equals(paymentRequest.getPaymentType())){
            return paymentService.makePayment(new CCPaymentMethod(ccAddress), paymentRequest.getOrderId());
        }

        return PaymentStatus.UNSUPPORTED;
    }
}
