package com.biggwang.web.service;

import com.biggwang.web.code.InvestResultEnum;
import com.biggwang.web.entity.ProductEntity;
import com.biggwang.web.exception.ProductException;
import com.biggwang.web.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.biggwang.web.code.Constant.INVESTOR_COUNT_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestStatusStoreMysqlServiceImpl implements InvestStatusStoreService {

    private final ProductRepository productRepository;

    @Override
    public Long saveInvestmentAmount(Integer productId, Long investmentAmount) {
        try {
            ProductEntity productEntity = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductException(InvestResultEnum.NOT_EXIST_PRODUCT));
            productEntity.setCurrentInvestingAmount(investmentAmount.intValue());
            return Long.valueOf(productRepository.save(productEntity).getProductId());
        } catch (Exception e) {
            log.error("saveInvestmentAmount to mysql because {}", e.getMessage());
            return -1L;
        }
    }

    @Override
    public Long restoreInvestmentAmount(Integer productId, Long investmentAmount) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(InvestResultEnum.NOT_EXIST_PRODUCT));
        productEntity.setCurrentInvestingAmount(-investmentAmount.intValue());
        return Long.valueOf(productRepository.save(productEntity).getProductId());
    }

    @Override
    public Integer saveInvestorCount(Integer productId, Integer totalInvestorCount) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(InvestResultEnum.NOT_EXIST_PRODUCT));
        productEntity.setTotalInvestor(totalInvestorCount);
        return productRepository.save(productEntity).getProductId();
    }

    @Override
    public Integer restoreInvestorCount(Integer productId, Integer totalInvestorCount) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(InvestResultEnum.NOT_EXIST_PRODUCT));
        productEntity.setTotalInvestor(-totalInvestorCount);
        return productRepository.save(productEntity).getProductId();
    }
}
