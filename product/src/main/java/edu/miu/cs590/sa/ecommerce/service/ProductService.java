package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDTO get(Long id);
    List<ProductDTO> getAll();
    ProductDTO save(ProductDTO product);
    ProductDTO add(Long productId, Long quantity);
    ProductDTO deduct(Long productId, Long quantity);

}
