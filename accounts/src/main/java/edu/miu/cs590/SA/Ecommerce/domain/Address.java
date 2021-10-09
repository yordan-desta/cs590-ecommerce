package edu.miu.cs590.SA.Ecommerce.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class Address {
    private String city;
    private String state;
    private String streetNumber;
    private String zip;
}
