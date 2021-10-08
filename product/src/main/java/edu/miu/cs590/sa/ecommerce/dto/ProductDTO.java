package edu.miu.cs590.sa.ecommerce.dto;

import edu.miu.cs590.sa.ecommerce.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String vendor;
    private Long quantity;
    private double price;
    private Category category;
}
