package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

public interface  PaymentMethod {
    String getPaymentUri();
    PaymentTypes getType();
}
