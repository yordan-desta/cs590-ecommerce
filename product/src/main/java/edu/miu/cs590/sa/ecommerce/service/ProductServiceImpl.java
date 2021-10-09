package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.Product;
import edu.miu.cs590.sa.ecommerce.dto.ProductDTO;
import edu.miu.cs590.sa.ecommerce.repository.ProductRepository;
import edu.miu.cs590.sa.ecommerce.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<ProductDTO> getAll() {
        return MapperUtil.mapList(repository.findAll(), ProductDTO.class);
    }

    @Override
    public ProductDTO get(Long id) {
        return MapperUtil.map(repository.getById(id), ProductDTO.class);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = MapperUtil.map(productDTO, Product.class);
        return MapperUtil.map(repository.save(product), ProductDTO.class);
    }

    @Override
    public ProductDTO add(Long productId, Long quantity) {
        Product product = repository.getById(productId);
        product.add(quantity);
        return MapperUtil.map(repository.save(product), ProductDTO.class);
    }

    @Override
    public ProductDTO deduct(Long productId, Long quantity) {
        Product product = repository.getById(productId);
        String response = product.deduct(quantity);
        if(Objects.equals(response, "FAILED")) return null;
        return MapperUtil.map(repository.save(product), ProductDTO.class);
    }

}
