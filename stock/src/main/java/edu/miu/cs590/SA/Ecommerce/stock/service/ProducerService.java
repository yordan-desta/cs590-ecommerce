package edu.miu.cs590.SA.Ecommerce.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    public static final String topic = "product";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishToTopic (String message){
        System.out.println("Publishing to topic "+topic);
        this.kafkaTemplate.send(topic, message);
    }
}
