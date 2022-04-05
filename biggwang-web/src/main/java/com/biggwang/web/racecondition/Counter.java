package com.biggwang.web.racecondition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Counter {

    private static Integer count = 0;
    private static boolean sync = false;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            service.execute(() -> {
                if (sync) {
                    synchronized (count) {
                        count++;
                    }
                    return;
                }
                count++;
            });
        }

        // 하지 않으면 main 스레드가 바로 종료되서 값이 맞지 않는건가?
        Thread.sleep(2000L);
        service.shutdown();

        System.out.println(count);
    }
}
