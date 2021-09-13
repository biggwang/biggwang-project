package com.biggwang.reactive.controller;

import com.biggwang.reactive.dto.InvestmentDto;
import com.biggwang.reactive.entity.InvestmentEntity;
import com.biggwang.reactive.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.CommonEnum;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping("/init")
    public Flux<InvestmentEntity> setUp() {
        Flux<InvestmentEntity> stream = Flux.range(1, 500000).map(i -> new InvestmentEntity(String.valueOf(i), "1"));
        return investmentService.update(stream);
        /*return investmentService.update(
                IntStream.rangeClosed(1, 2000000)
                        .boxed()
                        .map(i -> new InvestmentDto(String.valueOf(i), RandomStringUtils.randomAlphanumeric(10)))
                        .collect(Collectors.toList())
        );*/
    }

    @GetMapping("/investment")
    public Flux<InvestmentEntity> getInvestment() throws InterruptedException {
        return investmentService.getInvestmentList();
    }

    @GetMapping("/investment/{userId}")
    public Mono<InvestmentEntity> invest(@PathVariable String userId) {
        return investmentService.invest(userId + CommonEnum.OKAY.getMeesage());
    }

    @PostMapping("/investment")
    public Mono<InvestmentEntity> update(@RequestBody InvestmentDto investmentDto) {
        return investmentService.update(investmentDto);
    }

}
