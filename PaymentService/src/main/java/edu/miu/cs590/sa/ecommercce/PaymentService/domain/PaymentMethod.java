package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

public interface  PaymentMethod {
    Payment getPayment();
    String getPaymentUri();
    PaymentTypes getType();
}
