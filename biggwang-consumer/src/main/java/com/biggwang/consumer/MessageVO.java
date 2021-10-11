package com.biggwang.consumer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageVO {
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
