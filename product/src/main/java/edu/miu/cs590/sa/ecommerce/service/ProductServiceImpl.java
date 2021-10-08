package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.Product;
import edu.miu.cs590.sa.ecommerce.dto.ProductDTO;
import edu.miu.cs590.sa.ecommerce.repository.ProductRepository;
import edu.miu.cs590.sa.ecommerce.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
