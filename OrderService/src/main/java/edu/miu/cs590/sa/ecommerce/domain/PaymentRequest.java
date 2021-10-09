package edu.miu.cs590.sa.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String userId;
    private String paypalId;
    private String creditCardNumber;
    private String orderId;
    private Double balance;
}
