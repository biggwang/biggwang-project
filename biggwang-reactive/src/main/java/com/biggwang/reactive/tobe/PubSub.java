package com.biggwang.reactive.tobe;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PubSub {

    public static void main(String[] args) throws InterruptedException {
        // Publisher <- Obserable
        // Subscriber <- Observer

        // DB에서 가져온 값이라고 생각하자
        Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);
        ExecutorService es = Executors.newSingleThreadExecutor();

        Publisher publisher = new Publisher() {
            @Override
            public void subscribe(Subscriber subscriber) {
                Iterator<Integer> it = itr.iterator();
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        es.execute(() -> {
                            int i = 0;
                            // back pressure 역활
                            while (i++ < n) {
                                if (it.hasNext()) {
                                    subscriber.onNext(it.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        });
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println(Thread.currentThread().getName() +  " onSubscribe");
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(Thread.currentThread().getName() +  " onNext " + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
                // obserable 한계였던 것이 여기는 스펙으로 있음
            }

            @Override
            public void onComplete() {
                // obserable 한계였던 것이 여기는 스펙으로 있음
                System.out.println("onComplete");
            }
        };

        publisher.subscribe(subscriber);

        es.awaitTermination(10, TimeUnit.HOURS);
        es.shutdown();
    }
}
