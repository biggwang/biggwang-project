package com.biggwang.reactive.tobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteService {

    @RestController
    public static class MyController {

        @GetMapping("/service")
        public String service(String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service";
        }

        @GetMapping("/service2")
        public String service2(String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service2";
        }
    }

    public static void main(String[] args) {
        // 같은 프로젝트 내 서로 다른 서버를 띄우기 위해 필요
        System.setProperty("server.port", "7001");
        System.setProperty("server.tomcat.threads.max", "1000");
        SpringApplication.run(RemoteService.class);
    }
}
