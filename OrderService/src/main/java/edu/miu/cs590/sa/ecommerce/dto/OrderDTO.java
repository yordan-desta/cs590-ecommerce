package edu.miu.cs590.sa.ecommerce.dto;

import edu.miu.cs590.sa.ecommerce.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private Long id;
    private String userId;
    private PaymentType paymentType;
    private OrderStatus orderStatus;
    private List<Product> products;
    private ShippingAddress shippingAddress;
}
