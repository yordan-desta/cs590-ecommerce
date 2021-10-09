package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaypalPayment implements Payment{

    private String userId;

    private String paypalId;

    private Double balance;
}
