package org.learning.kafka.springboot.demo.controller;

import org.learning.kafka.springboot.demo.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consumer")
public class MessageConsumer {
    private final ConsumerService service;

    @Autowired
    public MessageConsumer(ConsumerService service) {
        this.service = service;
    }

    @GetMapping("subscribe/{topic}")
    public ResponseEntity<String> subscribe(@PathVariable(name = "topic") String topic) {
        if (service.subscribe(topic)) {
            return ResponseEntity.ok("Subscribed to topic: " + topic);
        } else {
            return ResponseEntity.badRequest().body("Already subscribed to topic: " + topic);
        }
    }

}
