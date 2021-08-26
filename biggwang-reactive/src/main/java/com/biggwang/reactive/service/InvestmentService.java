package com.biggwang.reactive.service;

import com.biggwang.reactive.dto.InvestmentDto;
import com.biggwang.reactive.entity.InvestmentEntity;
import com.biggwang.reactive.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;

    public Flux<InvestmentEntity> getInvestmentList() {
        return investmentRepository.findAll();
    }

    public Mono<InvestmentEntity> invest(String userId) {
        return investmentRepository.save(
                InvestmentEntity.builder()
                        .id(userId)
                        .title("hi:" + LocalDateTime.now())
                        .build()
        );
    }

    public Mono<InvestmentEntity> update(InvestmentDto investmentDto) {
        return investmentRepository.save(
                InvestmentEntity.builder()
                        .id(investmentDto.getUserId())
                        .title(investmentDto.getTitle())
                        .build()
        );
    }

}
