package org.learning.kafka.springboot.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.learning.kafka.springboot.demo.helper.FileHelper;
import org.learning.kafka.springboot.demo.helper.ThreadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Service
public class ConsumerService {
    private static final String THREAD_GROUP = "subscribers";

    private ThreadHelper threadHelper;
    private FileHelper fileHelper;

    @Value("${proj.kafka.bootstrap-server}")
    private String bootstrapServer;
    @Value("${proj.kafka.string-deserializer}")
    private String deserializerClass;
    @Value("${proj.kafka.consumer-group-name}")
    private String consumerGroup;
    @Value("${proj.kafka.offset}")
    private String offset;

    @Autowired
    public ConsumerService(ThreadHelper threadHelper, FileHelper fileHelper) {
        this.threadHelper = threadHelper;
        this.fileHelper = fileHelper;
    }

    public boolean subscribe(String topic) {
        if (threadHelper.checkIfSubscribedToTopic(topic)) {
            log.error("Already subscribed to topic: " + topic);
            return false;
        }
        Runnable subscriberThread = () -> createThreadAndSubscribeToTopic(topic);
        new Thread(new ThreadGroup(THREAD_GROUP), subscriberThread, "subscriber-" + topic.toLowerCase()).start();

        return true;
    }

    public void createThreadAndSubscribeToTopic(String topic) {
        KafkaConsumer<String, String> kafkaConsumer = createConsumer();

        try {
            kafkaConsumer.subscribe(Collections.singleton(topic));

            while (true) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));

                consumerRecords.iterator().forEachRemaining(record -> {
                            fileHelper.writeToFile(Thread.currentThread().getName(), record.value());
//                            System.out.println(Thread.currentThread().getName() + " : " + record.value());
                        }
                );
            }
        } finally {
            kafkaConsumer.close();
        }
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializerClass);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializerClass);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);

        return new KafkaConsumer<>(properties);
    }
}
