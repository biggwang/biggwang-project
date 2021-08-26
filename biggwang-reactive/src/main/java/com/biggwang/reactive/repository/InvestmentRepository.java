package com.biggwang.reactive.repository;

import com.biggwang.reactive.entity.InvestmentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends ReactiveMongoRepository<InvestmentEntity, String> {
}
