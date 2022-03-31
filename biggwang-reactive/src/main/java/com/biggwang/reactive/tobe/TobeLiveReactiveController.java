package com.biggwang.reactive.tobe;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class TobeLiveReactiveController {

    @Autowired
    MyService myService;

    Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();
    //RestTemplate rt = new RestTemplate();
    AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1))); // 비동기이지만 별도 스레드로 위임하는것

    @GetMapping("/callable")
    public Callable<String> callable() {
        log.info("callable");
        return () -> {
            log.info("async");
            Thread.sleep(2000);
            return "hello";
        };
    }

    /**
     * 호출되면 서블릿 스레드는 반환 되지만 응답은 계속 대기 중...
     *
     * @return
     */
    @GetMapping("/dr")
    public DeferredResult<String> dr() {
        log.info("dr");
        DeferredResult<String> dr = new DeferredResult<>(600000L);
        results.add(dr);
        return dr;
    }

    @GetMapping("/dr/count")
    public String drCount() {
        return String.valueOf(results.size());
    }

    @GetMapping("/dr/event")
    public String drEvent(String msg) {
        for (DeferredResult<String> dr : results) {
            dr.setResult("Hello " + msg);
            results.remove(dr);
        }
        return "OK";
    }

    /**
     * 데이터를 여러번 나누어서..
     *
     * @return
     */
    @GetMapping("/emitter")
    public ResponseBodyEmitter emitter() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().submit(() -> {
            for (int i = 1; 1 <= 50; i++) {
                emitter.send("<p>Stream " + i + "</p>");
                Thread.sleep(100);
            }
        });

        return emitter;
    }

    @GetMapping("/rest")
    //public ListenableFuture<ResponseEntity<String>> rest(int idx) {
    public DeferredResult<String> rest(int idx) {
        // [step1] callback은 sprig mvc에서 알아서 처리 해준다.
        //return rt.getForEntity("http://localhost:7001/service?req={}", String.class, "hello" + idx);

        // [step2]만약에 결과값을 가공해서 return 하고 싶을 때는?? 그건 DeferredResult 이용!
        DeferredResult<String> dr = new DeferredResult<>();
//        ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity("http://localhost:7001/service?req={}", String.class, "hello" + idx);
//        f1.addCallback(s -> {
//            dr.setResult(s.getBody() + "/work");
//        }, e -> {
//            dr.setErrorResult(e.getMessage());
//        });

        // [step3] 만약에 api콜 이후 다른 api콜이나 자체 서비스 호출을 하면?
//        ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity("http://localhost:7001/service?req={}", String.class, "hello" + idx);
//        f1.addCallback(s -> {
//            ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity("http://localhost:7001/service2?req={}", String.class, s.getBody());
//            f2.addCallback(s2 -> {
////                dr.setResult(s2.getBody());
//                ListenableFuture<String> f3 = myService.work(s2.getBody());
//                f3.addCallback(s3 -> {
//                    dr.setResult(s3);
//                }, e-> {
//                    dr.setErrorResult(e.getMessage());
//                });
//            }, e -> {
//                dr.setErrorResult(e.getMessage());
//            });
//        }, e -> {
//            dr.setErrorResult(e.getMessage());
//        });

        // [step4] 콜백 지옥 없애기기
//        Completion
//                .from(rt.getForEntity("http://localhost:7001/service?req={}", String.class, "hello" + idx))
//                .andApply(s -> rt.getForEntity("http://localhost:7001/service2?req={}", String.class, s.getBody())) // 입력값, 리턴값 필요하므로 function 개념
//                .andApply(s -> myService.work(s.getBody()))
//                .andError(e -> dr.setErrorResult(e))
//                .andAccept(s -> dr.setResult(s.getBody())); // 단순히 받아서 처리 void

        return dr;
    }

}
