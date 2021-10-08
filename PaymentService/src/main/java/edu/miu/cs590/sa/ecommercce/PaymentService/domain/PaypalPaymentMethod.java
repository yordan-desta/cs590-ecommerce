package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import lombok.Getter;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class PaypalPaymentMethod implements PaymentMethod{

    private final String serviceAddress;
    private final Payment payment;

    public PaypalPaymentMethod(String serviceAddress, PaypalPayment payment) {
        this.serviceAddress = serviceAddress;
        this.payment = payment;
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
