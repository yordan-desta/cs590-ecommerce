package edu.miu.cs590.sa.ecommercce.PaymentService.service;

import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentMethod;
import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentStatus;
import lombok.NonNull;

public interface PaymentService {
    PaymentStatus makePayment(PaymentMethod paymentMethod, @NonNull String orderId);
}
