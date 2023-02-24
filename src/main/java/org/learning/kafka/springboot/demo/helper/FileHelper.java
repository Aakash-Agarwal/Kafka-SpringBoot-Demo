package org.learning.kafka.springboot.demo.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
@Component
public class FileHelper {

    public void writeToFile(String topic, String message) {
        try {
            File file = new File(topic +"-Messages.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            Path filePath = Paths.get(file.getPath());

            List<String> fileContent = Files.readAllLines(filePath);
            fileContent.add(message);

            Files.write(filePath, fileContent, StandardOpenOption.WRITE);
        } catch (IOException e) {
            log.error("Failed to write message to file", e);
        }
    }
}
