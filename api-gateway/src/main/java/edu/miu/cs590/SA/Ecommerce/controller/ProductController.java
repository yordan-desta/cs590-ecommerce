package edu.miu.cs590.SA.Ecommerce.controller;

import edu.miu.cs590.SA.Ecommerce.constant.RestEndpoints;
import edu.miu.cs590.SA.Ecommerce.service.AccountService;
import edu.miu.cs590.SA.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestEndpoints.PRODUCT_PREFIX)
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public ResponseEntity<?> getAll(){
        return productService.findAll();
    }

    // Get a single product by id
    @GetMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Object> product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    // Update a product
    @PutMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Object productBody){
        Object product = productService.update(id, productBody);
        return ResponseEntity.ok(product);
    }

    // Get a single product by id
    @DeleteMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.ok("Account deleted successfully !");
    }
}
