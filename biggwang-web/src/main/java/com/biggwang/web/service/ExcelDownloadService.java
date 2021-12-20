package com.biggwang.web.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelDownloadService {

    public void download(String target) {
        log.info( target + " 엑셀을 다운로드 하였습니다.");
    }
}
