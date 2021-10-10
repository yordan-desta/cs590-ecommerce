package edu.miu.cs590.SA.Ecommerce.stock.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "product", groupId = "mygroup")
    public void consumerFromTopic(String message){
        System.out.println("Consumed message "+message);
    }
}
