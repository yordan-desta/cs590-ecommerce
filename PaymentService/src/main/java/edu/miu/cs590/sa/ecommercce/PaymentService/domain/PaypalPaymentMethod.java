package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PaypalPaymentMethod implements PaymentMethod{

    private final String serviceAddress;

    public PaypalPaymentMethod(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public String getPaymentUri() {
       return URLEncoder.encode(serviceAddress, StandardCharsets.UTF_8);
    }

    @Override
    public PaymentTypes getType() {
        return PaymentTypes.PAYPAL;
    }

}
