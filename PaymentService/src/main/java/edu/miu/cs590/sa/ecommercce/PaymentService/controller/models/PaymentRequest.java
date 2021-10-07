package edu.miu.cs590.sa.ecommercce.PaymentService.controller.models;

import edu.miu.cs590.sa.ecommercce.PaymentService.domain.PaymentTypes;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class PaymentRequest {
    @NonNull
    private String orderId;

    @NonNull
    private PaymentTypes paymentType;
}
