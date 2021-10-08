package edu.miu.cs590.sa.ecommercce.PaymentService.controller.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class CCPaymentRequest {
    @NonNull
    private String userId;

    @NonNull
    public String creditCardNumber;

    @NonNull
    public Double balance;

    @NonNull
    public String orderId;

}
