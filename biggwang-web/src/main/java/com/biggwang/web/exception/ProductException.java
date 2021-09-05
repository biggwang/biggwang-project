package com.biggwang.web.exception;

import com.biggwang.web.code.InvestResultEnum;
import lombok.Getter;

@Getter
public class ProductException extends RuntimeException{

    private final InvestResultEnum investResultEnum;

    public ProductException(InvestResultEnum investResultEnum) {
        super(investResultEnum.getMessage());
        this.investResultEnum = investResultEnum;
    }
}
