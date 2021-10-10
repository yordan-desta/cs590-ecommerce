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

    public ProducerMessage deduct(Long quantity) {
        if(this.quantity > quantity) {
            this.quantity -= quantity;
            return checkThreshold(ProductOperation.DEDUCT);
        }
        return ProducerMessage.ERROR;
    }
    public ProducerMessage add(Long quantity) {
            this.quantity += quantity;
            return checkThreshold(ProductOperation.ADD);
    }

    private ProducerMessage checkThreshold(ProductOperation operation) {
        if(this.quantity < thresholdQuantity) {
            log.info("Product [" + this.id + "] is below a minimum threshold.");
            return ProducerMessage.BELOW_THRESHOLD;
        }
        if(this.quantity >= thresholdQuantity && operation.equals(ProductOperation.ADD)) {
            log.info("Product [" + this.id + "] is restocked.");
            return ProducerMessage.RESTOCKED;
        }
        return ProducerMessage.NONE;
    }
}
