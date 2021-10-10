package edu.miu.cs590.sa.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {

    @Value("${kafka.publish.below_threshold_topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishToTopic(String message){
        log.info("Publishing to topic [" + topic + "] message: " + message);
        this.kafkaTemplate.send(topic, message);
    }
}
