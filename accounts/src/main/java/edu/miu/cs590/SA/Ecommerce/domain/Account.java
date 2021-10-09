package edu.miu.cs590.SA.Ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String username;
    private String password;

    @Embedded
    private Address shippingAddress;

    @OneToMany
    @JoinTable(name = "account_payment_type",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "payment_id")}
    )
    private Set<PaymentType> paymentMethods;

    @Column(name = "preferred_payment_method")
    private String preferredPaymentMethod;

}
