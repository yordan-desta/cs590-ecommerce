package edu.miu.cs590.sa.ecommerce.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String vendor;
    private Long quantity;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;
}
