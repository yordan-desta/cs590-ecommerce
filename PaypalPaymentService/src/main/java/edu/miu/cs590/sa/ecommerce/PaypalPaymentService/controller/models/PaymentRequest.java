package edu.miu.cs590.sa.ecommerce.PaypalPaymentService.controller.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@ToString
public class PaymentRequest{

    private String userId;

    private String paypalId;

    private Double balance;

}
