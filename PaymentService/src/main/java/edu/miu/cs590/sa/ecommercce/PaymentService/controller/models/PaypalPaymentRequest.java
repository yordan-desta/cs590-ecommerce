package edu.miu.cs590.sa.ecommercce.PaymentService.controller.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class PaypalPaymentRequest {
    @NonNull
    private String userId;

    @NonNull
    private String paypalId;

    @NonNull
    private Double balance;

    @NonNull
    public String orderId;

}
