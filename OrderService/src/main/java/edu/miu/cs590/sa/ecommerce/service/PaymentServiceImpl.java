package edu.miu.cs590.sa.ecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final String paypalPaymentServiceUri;
    private final String ccPaymentServiceUri;

    public PaymentServiceImpl(@Value("${payment.url.paypal}") String paypalPaymentServiceUri, @Value("${payment.url.cc}") String ccPaymentServiceUri) {
        this.paypalPaymentServiceUri = paypalPaymentServiceUri;
        this.ccPaymentServiceUri = ccPaymentServiceUri;
    }

    @Override
    public String getPaypalPaymentUri() {
        return this.paypalPaymentServiceUri;
    }
    @Override
    public String getCcPaymentUri() {
        return this.ccPaymentServiceUri;
    }
}
