package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.ProducerMessage;
import edu.miu.cs590.sa.ecommerce.domain.Product;
import edu.miu.cs590.sa.ecommerce.dto.ProductDTO;
import edu.miu.cs590.sa.ecommerce.repository.ProductRepository;
import edu.miu.cs590.sa.ecommerce.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${stock_url}")
    private String stockUrl;

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
        ProducerMessage response = product.add(quantity);
        ProductDTO productDTO = MapperUtil.map(repository.save(product), ProductDTO.class);
        return productDTO;
    }

    @Override
    public ProductDTO deduct(Long productId, Long quantity) {
        Product product = repository.getById(productId);
        ProducerMessage response = product.deduct(quantity);
        if(response.equals(ProducerMessage.ERROR)) return null;
        ProductDTO productDTO = MapperUtil.map(repository.save(product), ProductDTO.class);
        if(response == ProducerMessage.BELOW_THRESHOLD){
            log.info("sending message [" + "Deduct stock from: " + product.getName() + "] to " + stockUrl);
            restTemplate.postForEntity(stockUrl, "Deduct stock from: " + product.getName(), Objects.class);
        }
        return productDTO;
    }

}
