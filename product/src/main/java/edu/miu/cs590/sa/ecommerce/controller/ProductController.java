package edu.miu.cs590.sa.ecommerce.controller;

import edu.miu.cs590.sa.ecommerce.constants.RestEndpoints;
import edu.miu.cs590.sa.ecommerce.dto.ProductDTO;
import edu.miu.cs590.sa.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestEndpoints.PRODUCTS)
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.get(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }
}
