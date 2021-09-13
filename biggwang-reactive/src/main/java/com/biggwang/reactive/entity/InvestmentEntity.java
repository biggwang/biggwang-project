package com.biggwang.reactive.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection="investment")
public class InvestmentEntity {

    @Id
    private String id;

    private String title;

    @Builder
    public InvestmentEntity(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
