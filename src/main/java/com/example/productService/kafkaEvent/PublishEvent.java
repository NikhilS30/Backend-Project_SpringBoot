package com.example.productService.kafkaEvent;

import com.example.productService.dto.EmailDto;
import com.example.productService.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishEvent {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PublishEvent(KafkaTemplate<String, String> kafkaTemplate,
                        ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishEvent(Product savedProduct) {

        try {
            // ✅ Create DTO per request (thread-safe)
            EmailDto emailDto = new EmailDto();
            emailDto.setId(savedProduct.getId());
            emailDto.setTo("sknikhil2000@gmail.com");
            emailDto.setSubject("Kafka Testing");
            emailDto.setBody("Hey, your order is placed. Notifying you from Kafka!");

            // ✅ Convert to JSON
            String message = objectMapper.writeValueAsString(emailDto);

            // ✅ Publish to Kafka
            kafkaTemplate.send("sendEmail", message);

        } catch (Exception e) {
            // ✅ Proper error handling
            throw new RuntimeException("Failed to publish event to Kafka", e);
        }
    }
}