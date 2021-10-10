package edu.miu.cs590.sa.ecommerce.controller;

import edu.miu.cs590.sa.ecommerce.domain.OrderStatus;
import edu.miu.cs590.sa.ecommerce.constants.RestEndpoints;
import edu.miu.cs590.sa.ecommerce.domain.PaymentInfo;
import edu.miu.cs590.sa.ecommerce.domain.PaymentType;
import edu.miu.cs590.sa.ecommerce.dto.OrderDTO;
import edu.miu.cs590.sa.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestEndpoints.ORDERS)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.get(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO order) {
        OrderDTO orderDTO = orderService.save(order);
        if(orderDTO == null) return new ResponseEntity<>("Order create failed", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
       return new ResponseEntity<Object>(orderService.updateStatus(id, status), HttpStatus.OK);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> pay(@PathVariable Long id, @RequestBody PaymentInfo paymentInfo) {
        OrderDTO orderDTO = orderService.pay(id, paymentInfo);
        if(orderDTO == null) return new ResponseEntity<>("Payment failed", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}
