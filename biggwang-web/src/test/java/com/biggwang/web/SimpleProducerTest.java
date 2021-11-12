package com.biggwang.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class SimpleProducerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String topic = "test1";

    @Test
    void sendMessage1() {
        IntStream.rangeClosed(1, 10).boxed().parallel().forEach(item -> {
            String objString = null;
            try {
                MessageVO messageVO = new MessageVO();
                //messageVO.setKey(UUID.randomUUID().toString());
                messageVO.setKey(item.toString());
                messageVO.setName(RandomStringUtils.randomAlphabetic(5));
                messageVO.setAge(34);
                messageVO.setCity("광명");
                objString = objectMapper.writeValueAsString(messageVO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            kafkaTemplate.send(topic, objString);
        });
    }

    @Getter
    @Setter
    static class MessageVO {
        private String key;
        private String name;
        private Integer age;
        private String city;

        public MessageVO() {
        }

        public MessageVO(String key, String name, Integer age, String city) {
            this.key = key;
            this.name = name;
            this.age = age;
            this.city = city;
        }
    }
}
