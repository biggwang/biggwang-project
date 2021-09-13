package com.biggwang.reactive.service;

import com.biggwang.reactive.dto.InvestmentDto;
import com.biggwang.reactive.entity.InvestmentEntity;
import com.biggwang.reactive.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;

    public Flux<InvestmentEntity> getInvestmentList() {
        Flux<InvestmentEntity> result = investmentRepository.findAll();
        return result;
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

    public Flux<InvestmentEntity> update(Flux<InvestmentEntity> investmentDtoList) {
        Flux<InvestmentEntity> result = investmentRepository.saveAll(investmentDtoList);
        return result;
    }
}
