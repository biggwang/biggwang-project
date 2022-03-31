package com.biggwang.reactive.tobe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {

    // 여러스레드에서 레이스 컨디션이 발생 했을 때, 스레드 세이프 하게 카운팅 하기 위해
    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:7000/rest2?req={req}";

        // await() 101 번 호출 될 때까지 스레드를 blocking 시키고 이후 한꺼번에 코드 호출
        // for 문 돌때 순차적으로 api 콜하는게 아니라
        // 모았다가 한꺼번에 빵! 하고 요청하기 위함
        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            // execute 메소드는 runnable 이기 때문에 throw exception 할 수가 없다.
            // 그래서 submit으로 변경
            es.submit(() -> {
                //log.info("Thread " + i); // i를 사용하면 오류 나는 이유, 람다식안에서 다른스레드에서 로컬 i 변수를 참조 할 수 없으니까
                int idx = counter.addAndGet(1);
                log.info("Thread {}", idx);

                // 여기로 진입이 100번이 될때까지 blocking이 된다.
                barrier.await();

                StopWatch sw = new StopWatch();
                sw.start();

                String res = rt.getForObject(url, String.class, idx);

                sw.stop();
                log.info("Elapsed: {} / {} / {}", idx, sw.getTotalTimeSeconds(), res);

                // 리턴값이 있어야 컴파일러가 callable이라고 인식하여 throw exception 할 수 있게 됨
                return null;
            });
        }

        barrier.await();
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("Total: {}", main.getTotalTimeSeconds());
    }
}
