package com.biggwang.web.repository;

import com.biggwang.web.entity.InvestmentEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
class InvestmentRepositoryTest {

    @Autowired
    private InvestmentRepository investmentRepository;
    private final Integer productId = 4;

    @BeforeEach
    void setup() {
        investmentRepository.deleteAll();
    }

    @Test
    @DisplayName("투자 INSERT 테스트")
    void save() {
        investmentRepository.save(
                InvestmentEntity.builder()
                        .productId(productId)
                        .userId(Integer.parseInt(RandomStringUtils.randomNumeric(5)))
                        .investmentAmount(Integer.parseInt(RandomStringUtils.randomNumeric(5)))
                        .build()
        );
    }

    @Test
    @DisplayName("내가 투자한 상품이 있는지 조회 테스트")
    void existsByProductIdAndUserId() {
        // given
        Integer randomUserId = Integer.parseInt(RandomStringUtils.randomNumeric(5));
        InvestmentEntity investmentEntity = getInvestmentEntity(productId, randomUserId, 1000);
        investmentRepository.save(investmentEntity);
        // when
        boolean result =  investmentRepository.existsByProductIdAndUserId(investmentEntity.getProductId(), randomUserId);
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("내가 투자한 내역 조회 테스트")
    void getInvestmentList() {
        // given
        Integer randomUserId = Integer.parseInt(RandomStringUtils.randomNumeric(5));
        investmentRepository.saveAll(
                Arrays.asList(
                        getInvestmentEntity(productId, randomUserId, 1000),
                        getInvestmentEntity(productId, randomUserId, 3000),
                        getInvestmentEntity(productId, randomUserId, 5000)
                )
        );
        // when
        List<InvestmentEntity> list = investmentRepository.findByUserIdOrderByInvestmentAtDesc(randomUserId);
        //then
        assertEquals(list.size(), 3);
    }

    private InvestmentEntity getInvestmentEntity(Integer productId, Integer userId, Integer investmentAmount) {
        return InvestmentEntity.builder()
                .productId(productId)
                .userId(userId)
                .investmentAmount(investmentAmount)
                .build();
    }
}