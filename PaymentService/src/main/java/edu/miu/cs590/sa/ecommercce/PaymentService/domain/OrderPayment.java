package edu.miu.cs590.sa.ecommercce.PaymentService.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderPayment {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Column(name = "payment_uuid", nullable = false)
    private String uuid;

    private PaymentTypes paymentType;

    private PaymentStatus status;

    private String orderId;

    private String transactionId;

    public OrderPayment(){
        if(uuid == null)
            this.uuid = UUID.randomUUID().toString();
    }
}
