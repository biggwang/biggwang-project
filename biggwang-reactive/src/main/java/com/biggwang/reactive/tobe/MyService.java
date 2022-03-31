package com.biggwang.reactive.tobe;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class MyService {

    public ListenableFuture<String> work(String req) {
        return new AsyncResult<>(req + "asyncwork");
    }

    @Bean
    public ThreadPoolTaskExecutor myThread() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(1);
        te.setMaxPoolSize(1);
        te.initialize();
        return te;
    }
}
