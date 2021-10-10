package edu.miu.cs590.SA.Ecommerce.controller;

import edu.miu.cs590.SA.Ecommerce.constant.RestEndpoints;
import edu.miu.cs590.SA.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(RestEndpoints.ORDER_PREFIX)
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<?> getAll(){

        return orderService.findAll();
    }

    // Get a single order by id
    @GetMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Object> product = orderService.findById(id);
        return ResponseEntity.ok(product);
    }

    // Create an order
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Object orderBody){
        Object product = orderService.create(orderBody);
        return ResponseEntity.ok(product);
    }

    @PostMapping(RestEndpoints.BY_ID+RestEndpoints.PAY_POSTFIX)
    public ResponseEntity<?> pay(@PathVariable Long id, @RequestBody Object paymentBody){
        Object paymentInfo = orderService.pay(id, paymentBody);
        return ResponseEntity.ok(paymentInfo);
    }

    // Update an order
    @PutMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Object orderBody){
        Object product = orderService.update(id, orderBody);
        return ResponseEntity.ok(product);
    }

    // Get a single order by id
    @DeleteMapping(RestEndpoints.BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("Account deleted successfully !");
    }
}
