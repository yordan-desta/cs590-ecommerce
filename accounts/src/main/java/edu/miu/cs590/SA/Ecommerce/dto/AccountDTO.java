package edu.miu.cs590.SA.Ecommerce.dto;

import edu.miu.cs590.SA.Ecommerce.domain.Address;
import edu.miu.cs590.SA.Ecommerce.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private  String username;
    private Address shippingAddress;
    private Set<PaymentType> paymentMethods;
    private String preferredPaymentMethod;

}
