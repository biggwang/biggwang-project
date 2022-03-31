package com.biggwang.reactive;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@RestController
@SpringBootApplication
public class ReactiveApplication {

    WebClient webClient = WebClient.create();

    @GetMapping("/rest2")
    public Mono<String> rest2(int req) {
        return webClient.get().uri("http://localhost:7001/service?req={req}", req).exchange()
                .log()
                .flatMap(c -> c.bodyToMono(String.class))
                .log()
                .flatMap(res1 -> webClient.get().uri("http://localhost:7001/service2?req={req}", res1).exchange())
                .log()
                .flatMap(c -> c.bodyToMono(String.class))
                .log()
                ;
    }

    public static void main(String[] args) {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
        SpringApplication.run(ReactiveApplication.class, args);
    }

}
