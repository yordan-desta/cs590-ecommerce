package edu.miu.cs590.sa.ecommerce.service;

public interface PaymentService {
    String getPaypalPaymentUri();
    String getCcPaymentUri();
}
