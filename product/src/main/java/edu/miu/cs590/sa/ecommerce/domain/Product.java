package edu.miu.cs590.sa.ecommerce.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
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
    private Long thresholdQuantity;

    public String deduct(Long quantity) {
        if(this.quantity > quantity) {
            this.quantity -= quantity;
            checkThreshold();
            return "SUCCESS";
        }
        return "FAILED";
    }
    public void add(Long quantity) {
            this.quantity += quantity;
            checkThreshold();
    }

    private void checkThreshold() {
        if(this.quantity < thresholdQuantity) {
            //TODO publish below threshold message
            log.info("Product [" + this.id + "] is below a minimum threshold.");
        }
        if(this.quantity >= thresholdQuantity) {
            //TODO publish restocked message
            log.info("Product [" + this.id + "] is restocked.");
        }
    }
}