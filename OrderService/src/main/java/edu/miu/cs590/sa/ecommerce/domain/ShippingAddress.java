package edu.miu.cs590.sa.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class ShippingAddress {
    private String state;
    private String city;
    private String zip;
    private String street;
}
