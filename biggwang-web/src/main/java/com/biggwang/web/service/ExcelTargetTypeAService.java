package com.biggwang.web.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelTargetTypeAService implements ExcelTargetService {

    @Override
    public String getTarget() {
        log.info("TypeA 대상자 추출하였습니다.");
        return "TypeA";
    }
}
