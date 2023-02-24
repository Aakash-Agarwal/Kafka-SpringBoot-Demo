package org.learning.kafka.springboot.demo.controller;

import org.learning.kafka.springboot.demo.entitiy.request.MessageEntity;
import org.learning.kafka.springboot.demo.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
public class MessageProducer {

    private final ProducerService producerService;

    @Autowired
    public MessageProducer(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("publish")
    public ResponseEntity<String> publishMessage(@RequestBody MessageEntity messageEntity) {
        producerService.publishMessage(messageEntity);
        return ResponseEntity.ok("Successfully published message to topic: " + messageEntity.getTopic());
    }

}
