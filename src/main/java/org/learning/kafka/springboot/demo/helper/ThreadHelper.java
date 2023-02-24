package org.learning.kafka.springboot.demo.helper;

import org.springframework.stereotype.Component;

@Component
public class ThreadHelper {

    public boolean checkIfSubscribedToTopic(String topic) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (("subscriber-" + topic.toLowerCase()).equalsIgnoreCase(thread.getName()) && thread.isAlive()) {
                return true;
            }
        }

        return false;
    }
}
