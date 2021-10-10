package edu.miu.cs590.sa.ecommerce.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany
    @JoinTable(name = "order_product",
        joinColumns={@JoinColumn(name = "order_id")},
        inverseJoinColumns = {@JoinColumn(name="product_id")})
    private List<Product> products;

    @Embedded
    private ShippingAddress shippingAddress;

}
