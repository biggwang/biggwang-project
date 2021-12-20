package com.biggwang.web.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelTargetTypeBService implements ExcelTargetService {

    @Override
    public String getTarget() {
        log.info("TypeB 대상자 추출하였습니다.");
        return "TypeB";
    }
}
