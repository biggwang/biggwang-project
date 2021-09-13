package com.biggwang.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReactiveOperationTest {

    @Test
    void createAFlux_fromArray() {
        String[] fruits = new String[]{"Apple", "Orange"};
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .verifyComplete();
    }

    @Test
    void createAFlux_fromIterable() {
        List<String> fruits = Arrays.asList("Apple", "Orange");
        Flux<String> fruitFlux = Flux.fromIterable(fruits);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .verifyComplete();
    }

    @Test
    void createAFlux_fromStream() {
        Stream<String> fruits = Stream.of("Apple", "Orange");
        Flux<String> fruitFlux = Flux.fromStream(fruits);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .verifyComplete();
    }

    @Test
    void createAFlux_range() {
        Flux<Integer> intervalFlux = Flux.range(1, 5);
        StepVerifier.create(intervalFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    void createAFlux_interval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }

    @Test
    void mergeFluxes() {
        Flux<String> typeA = Flux.just("루피", "조로", "상디");
        Flux<String> typeB = Flux.just("에이스", "마르코", "조즈");
        Flux<String> mergedFlux = typeA.mergeWith(typeB);
        StepVerifier.create(mergedFlux)
                .expectNext("루피")
                .expectNext("조로")
                .expectNext("상디")
                .expectNext("에이스")
                .expectNext("마르코")
                .expectNext("조즈")
                .verifyComplete();
    }

    @Test
    void zipFluxes() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);
        StepVerifier.create(zippedFlux)
                .expectNextMatches(p ->
                        p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
                .expectNextMatches(p ->
                        p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
                .expectNextMatches(p ->
                        p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
                .verifyComplete();
    }
}
