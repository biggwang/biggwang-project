package com.biggwang.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SimpleConsumer {

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "test1", groupId = "group-1")
    public void raceCondition01(List<String> messages) {
        messages.forEach(item -> {
            try {
                MessageVO messageVO =  objectMapper.readValue(item, MessageVO.class);
                log.warn("##################### messageVO:{}!", messageVO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @KafkaListener(topics = "test1", groupId = "group-3")
    public void raceCondition03(List<String> messages) {
        messages.forEach(item -> {
            try {
                MessageVO messageVO =  objectMapper.readValue(item, MessageVO.class);
                log.warn("##################### messageVO by group3:{}!", messageVO.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
