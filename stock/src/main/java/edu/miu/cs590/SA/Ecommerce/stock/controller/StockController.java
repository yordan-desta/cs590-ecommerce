package edu.miu.cs590.SA.Ecommerce.stock.controller;

import edu.miu.cs590.SA.Ecommerce.stock.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    ProducerService producerService;

    @Autowired
    public StockController(ProducerService producerService){
        this.producerService = producerService;
    }

    @PostMapping(value = "/post")
    public void sendMessage(@RequestParam("msg") String msg){
        producerService.publishToTopic(msg);
    }
}
