package com.biggwang.reactive.controller;

import com.biggwang.reactive.entity.InvestmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class WebClientTestController {

    @GetMapping("/webclient/investment")
    public Flux<InvestmentEntity> getInvestments() {
        Flux<InvestmentEntity> result = WebClient.create("http://localhost:7000").get()
                .uri("/investment")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(InvestmentEntity.class);
        result.subscribe(f -> {
            System.out.println("cousumming:" + f);
        });
        return result;
    }
}
