package org.learning.kafka.springboot.demo;

import org.learning.kafka.springboot.demo.controller.MessageConsumer;
import org.learning.kafka.springboot.demo.controller.MessageProducer;
import org.learning.kafka.springboot.demo.entitiy.request.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @PostConstruct
    public void testApplicationInMultithreadedEnv() {
        produceTable(2);
        produceTable(3);
        produceTable(4);
        produceTable(5);
        produceTable(6);

        consumeTable(2);
        consumeTable(3);
        consumeTable(4);
        consumeTable(5);
        consumeTable(6);
    }

    private void consumeTable(int number) {
        MessageConsumer consumer = applicationContext.getBean(MessageConsumer.class);
        consumer.subscribe("tableOf" + number);
        consumer.subscribe("tableOf" + number);
    }

    private void produceTable(int number) {
        MessageProducer producer = applicationContext.getBean(MessageProducer.class);
        for (int times = 1; times <= 10; times++) {
            MessageEntity message = new MessageEntity("tableOf" + number, times + " times " + number + " is = " + number*times);
            producer.publishMessage(message);
        }
    }
}
