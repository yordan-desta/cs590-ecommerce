package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.Product;

import java.util.List;

public interface ProductService {
    String getProductUri();
    List<Product> saveAll(List<Product> products);
}
