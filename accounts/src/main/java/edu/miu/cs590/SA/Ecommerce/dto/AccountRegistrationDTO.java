package edu.miu.cs590.SA.Ecommerce.dto;

import edu.miu.cs590.SA.Ecommerce.domain.Address;
import edu.miu.cs590.SA.Ecommerce.domain.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String preferredPaymentType;
    private Address shippingAddress;
}
