package com.biggwang.web.service;

public class ExcelTargetFactory {

    private ExcelTargetTypeAService excelTargetTypeAService;
    private ExcelTargetTypeBService excelTargetTypeBService;

    public ExcelTargetFactory(ExcelTargetTypeAService excelTargetTypeAService, ExcelTargetTypeBService excelTargetTypeBService) {
        this.excelTargetTypeAService = excelTargetTypeAService;
        this.excelTargetTypeBService = excelTargetTypeBService;
    }

    public ExcelTargetService get(String type) {
        if ("A".equals(type)) {
            return excelTargetTypeAService;
        } else if ("B".equals(type)) {
            return excelTargetTypeBService;
        } else {
            throw new IllegalArgumentException("");
        }
    }
}
