package org.learning.kafka.springboot.demo.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.learning.kafka.springboot.demo.entitiy.request.MessageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ProducerService {
    private KafkaProducer<String, String> KAFKA_PRODUCER;

    @Value("${proj.kafka.bootstrap-server}")
    private String bootstrapServer;
    @Value("${proj.kafka.string-serializer}")
    private String serializerClass;

    public void publishMessage(MessageEntity messageEntity) {
        if (KAFKA_PRODUCER == null) {
            createProducer();
        }

        KAFKA_PRODUCER.send(new ProducerRecord<>(messageEntity.getTopic(), messageEntity.getMessage()));
    }

    private void createProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializerClass);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializerClass);

        KAFKA_PRODUCER = new KafkaProducer<>(properties);
    }
}
