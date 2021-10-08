package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CCPayment implements Payment{
    private String userId;
    private String creditCardNumber;
    private Double balance;
}
