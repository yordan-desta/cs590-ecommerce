package edu.miu.cs590.sa.ecommerce.config;

import edu.miu.cs590.sa.ecommerce.domain.Category;
import edu.miu.cs590.sa.ecommerce.domain.Product;
import edu.miu.cs590.sa.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ProductInit {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void seedProductsData() {
        createProducts();
    }

    @Transactional
    public void createProducts() {
        List<Product> products = List.of(
                new Product(1L, "product 1", "vendor A", 250L, 15.49, Category.APPAREL,50L),
                new Product(2L, "product 2", "vendor A", 1000L, 10.0, Category.ELECTRONICS,100L),
                new Product(3L, "product 3", "vendor A", 550L, 15.99, Category.FOOD,150L),
                new Product(4L, "product 4", "vendor B", 700L, 25.0, Category.APPAREL,100L),
                new Product(5L, "product 5", "vendor B", 1250L, 15.0, Category.ELECTRONICS,250L),
                new Product(6L, "product 6", "vendor B", 450L, 20.50, Category.FOOD,50L),
                new Product(7L, "product 7", "vendor C", 1500L, 50.99, Category.APPAREL,150L),
                new Product(8L, "product 8", "vendor C", 250L, 15.50, Category.ELECTRONICS,25L),
                new Product(9L, "product 9", "vendor C", 150L, 150.0, Category.FOOD,20L),
                new Product(10L, "product 10", "vendor D", 100L, 200.0, Category.APPAREL,10L)
        );
        productRepository.saveAll(products);
    }
}
