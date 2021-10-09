package edu.miu.cs590.sa.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
    private String paypalId;
    private String creditCardNumber;
}
