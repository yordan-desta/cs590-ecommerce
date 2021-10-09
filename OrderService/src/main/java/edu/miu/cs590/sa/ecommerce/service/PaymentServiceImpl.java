package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.PaymentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.url.paypal}")
    private String paypalPaymentServiceUri;

    @Value("${payment.url.cc}")
    private String ccPaymentServiceUri;

    @Override
    public String getPaypalPaymentUri() {
        return this.paypalPaymentServiceUri;
    }
    @Override
    public String getCcPaymentUri() {
        return this.ccPaymentServiceUri;
    }
}
