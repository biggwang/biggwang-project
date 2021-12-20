package com.biggwang.web.service;

public class ExcelDownloadFacade {

    private final ExcelTargetFactory excelTargetService;
    private final ExcelDownloadService excelDownloadService;
    private final NotifyService notifyService;

    public ExcelDownloadFacade(ExcelTargetFactory excelTargetFactory, ExcelDownloadService excelDownloadService, NotifyService notifyService) {
        this.excelTargetService = excelTargetFactory;
        this.excelDownloadService = excelDownloadService;
        this.notifyService = notifyService;
    }

    public void execute(String type) {
        String target = excelTargetService.get(type).getTarget();
        excelDownloadService.download(target);
        notifyService.send();
    }
}
