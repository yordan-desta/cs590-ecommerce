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

    @PostMapping("/{id}/deduct/{quantity}")
    public ResponseEntity<?> deductProductQuantity(@PathVariable Long id, @PathVariable Long quantity) {
        ProductDTO productDTO = productService.deduct(id, quantity);
        //TODO prepare an error response object
        if(productDTO == null) return new ResponseEntity<>("The requested quantity is beyond the stock on hand.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/add/{quantity}")
    public ResponseEntity<ProductDTO> addProductQuantity(@PathVariable Long id, @PathVariable Long quantity) {
        return new ResponseEntity<>(productService.add(id, quantity), HttpStatus.OK);
    }
}
