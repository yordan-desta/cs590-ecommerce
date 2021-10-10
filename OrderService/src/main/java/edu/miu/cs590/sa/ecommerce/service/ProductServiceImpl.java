package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.Product;
import edu.miu.cs590.sa.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${product.url}")
    private String productUri;

    @Autowired
    private ProductRepository repository;

    @Override
    public String getProductUri() {
        return this.productUri;
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return repository.saveAll(products);
    }
}
