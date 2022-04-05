package com.biggwang.web.racecondition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Order {

    public static int threads = 10;
    public static int users = 100;
    public static int MAX_GOODS = 97;
    private static Integer orderedGoodsCount = 0;
    private static boolean sync = true;

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < users; i++) {
            ex.execute(() -> {
                int idx = counter.addAndGet(1);
                if (sync) {
                    synchronized (orderedGoodsCount) {
                        order(idx);
                    }
                    return;
                }
                order(idx);
            });
        }
        Thread.sleep(2000L);
        ex.shutdown();

        log.warn("ordered goods:{}", orderedGoodsCount);
    }

    private static void order(int idx) {
        if (orderedGoodsCount >= MAX_GOODS) {
            log.info("### [{}] sold out!", idx);
            return;
        }
        orderedGoodsCount++;
    }
}
