package edu.miu.cs590.sa.ecommercce.PaymentService.service;

import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentMethod;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentStatus;

public interface PaymentService {
    PaymentStatus makePayment(PaymentMethod paymentMethod, String orderId);
}
