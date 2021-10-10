package edu.miu.cs590.SA.Ecommerce.stock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(topics ="#{'${kafka.publish.below_threshold_topic}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
    public void consumerFromTopic(String message){
        if(!message.isEmpty()){
            log.info("Consumed info --- " + message);
        }
    }
}
