package com.biggwang.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @Test
    void zipFluxesToObject() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
        StepVerifier.create(zippedFlux)
                .expectNext("Garfield eats Lasagna")
                .expectNext("Kojak eats Lollipops")
                .expectNext("Barbossa eats Apples")
                .verifyComplete();
    }

    @Test
    void firstFlux() {
        Flux<String> slowFlux = Flux.just("Garfield", "Kojak", "Barbossa").delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);
        StepVerifier.create(firstFlux)
                .expectNext("Lasagna")
                .expectNext("Lollipops")
                .expectNext("Apples")
                .verifyComplete();
    }

    @Test
    void skipAFew() {
        Flux<String> skipFlux = Flux.just("one", "two", "three", "four", "five").skip(3);
        StepVerifier.create(skipFlux)
                .expectNext("four", "five")
                .verifyComplete();
    }

    @Test
    void take() {
        Flux<String> takeFlux = Flux.just("one", "two", "three", "four", "five").take(3);
        StepVerifier.create(takeFlux)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }

    @Test
    void filter() {
        Flux<Integer> origin = Flux.just(1, 2, 3, 4, 5).filter(item -> item % 2 == 0);
        StepVerifier.create(origin)
                .expectNext(2, 4)
                .verifyComplete();
    }

    @Test
    void map() {
        Flux<String> origin = Flux.just("루피", "조로").map(item -> item + " 가즈아!!");
        StepVerifier.create(origin)
                .expectNext("루피 가즈아!!")
                .expectNext("조로 가즈아!!")
                .verifyComplete();
    }

    @Test
    void buffer() {
        Flux<Integer> origin = Flux.just(1, 2, 3, 4, 5);

        Flux<List<Integer>> bufferedFlux = origin.buffer(3);
        StepVerifier.create(bufferedFlux)
                .expectNext(Arrays.asList(1, 2, 3))
                .expectNext(Arrays.asList(4, 5))
                .verifyComplete();

        // flux형태가 아니라 List 컬렉션은 비효율적 그래서 flatMap 사용
        origin.buffer(3)
                .flatMap(item ->
                        Flux.fromIterable(item)
                                .map(i -> i + 1)
                                .subscribeOn(Schedulers.parallel())
                                .log()
                ).subscribe();
    }

    @Test
    void collectList() {
        Flux<Integer> origin = Flux.just(1, 2, 3, 4, 5);
        Mono<List<Integer>> originMono = origin.collectList();
        StepVerifier.create(originMono)
                .expectNext(Arrays.asList(1, 2, 3, 4, 5))
                .verifyComplete();
    }

    @Test
    void collectionMap() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));
        StepVerifier.create(animalMapMono)
                .expectNextMatches(map -> {
                    return map.size() == 3 &&
                            map.get('a').equals("aardvark") &&
                            map.get('e').equals("eagle") &&
                            map.get('k').equals("kangaroo");
                })
                .verifyComplete();
    }

    @Test
    void all() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> hasMono = animalFlux.all(a -> a.contains("a"));
        StepVerifier.create(hasMono).expectNext(true).verifyComplete();
    }

    @Test
    void any() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> hasMono = animalFlux.any(a -> a.contains("t"));
        StepVerifier.create(hasMono).expectNext(true).verifyComplete();
    }
}
