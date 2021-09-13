package com.biggwang.reactive.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentDto {

    private String userId;

    private String title;

    @Builder
    public InvestmentDto(String userId, String title) {
        this.userId = userId;
        this.title = title;
    }
}
