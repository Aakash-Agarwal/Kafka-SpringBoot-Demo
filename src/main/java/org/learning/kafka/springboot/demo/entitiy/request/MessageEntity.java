package org.learning.kafka.springboot.demo.entitiy.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MessageEntity {
    private String topic;
    private String message;
}
