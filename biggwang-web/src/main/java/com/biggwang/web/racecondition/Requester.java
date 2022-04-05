package com.biggwang.web.racecondition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Requester {

    // 여러스레드에서 레이스 컨디션이 발생 했을 때, 스레드 세이프 하게 카운팅 하기 위해
    static AtomicInteger counter = new AtomicInteger(0);
    static int loopCount = 1000;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(5);

        RestTemplate rt = new RestTemplate();
//        String url = "http://localhost:7100/sync/order?idx={idx}";
        String url = "http://localhost:7100/redis/sync/order/{user}";
//        String url = "http://localhost:7100/redis/order/{user}";

        for (int i = 0; i < loopCount; i++) {
            // execute 메소드는 runnable 이기 때문에 throw exception 할 수가 없다.
            // 그래서 submit으로 변경
            es.submit(() -> {
                int idx = counter.addAndGet(1);
                String res = rt.getForObject(url, String.class, idx);
                log.info("response:{}", res);
                return null;
            });
        }
    }
}
