package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import lombok.Getter;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class CCPaymentMethod implements PaymentMethod{

    private final String serviceAddress;

    private final Payment payment;

    public CCPaymentMethod(String serviceAddress, CCPayment payment) {
        this.serviceAddress = serviceAddress;
        this.payment = payment;
    }

    @Override
    public String getPaymentUri() {
        return serviceAddress;
    }

    @Override
    public PaymentTypes getType() {
        return PaymentTypes.CC;
    }

}
