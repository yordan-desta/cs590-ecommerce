package edu.miu.cs590.sa.ecommerce.repository;

import edu.miu.cs590.sa.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}