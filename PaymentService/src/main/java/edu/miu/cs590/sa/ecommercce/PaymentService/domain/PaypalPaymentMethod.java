package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import org.springframework.beans.factory.annotation.Value;

public class PaypalPaymentMethod implements PaymentMethod{

    @Value("${payment.url.paypal}")
    private String serviceAddress;

    @Override
    public String getPaymentUri() {
        return serviceAddress;
    }

    @Override
    public PaymentTypes getType() {
        return PaymentTypes.PAYPAL;
    }
}
