package com.biggwang.web.repository;

import com.biggwang.web.entity.InvestmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends CrudRepository<InvestmentEntity, Integer> {

    List<InvestmentEntity> findByUserIdOrderByInvestmentAtDesc(Integer userID);

    boolean existsByProductIdAndUserId(Integer productId, Integer userId);
}
