package com.biggwang.reactive.controller;

import com.biggwang.reactive.dto.InvestmentDto;
import com.biggwang.reactive.entity.InvestmentEntity;
import com.biggwang.reactive.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.CommonEnum;

@RestController
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping("/investment")
    public Flux<InvestmentEntity> getInvestment() {
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
