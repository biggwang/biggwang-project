package com.biggwang.reactive.tobe;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
public class Ob {

    static class IntObservable extends Observable implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i);     // push 하는 방식 (파라미터에 데이터를 넣고 있음)
                // int i = it.next();   // pulling 하는 방식 (파라미터가 없고 데이터를 받고 있음)
            }
        }
    }

    // main 스레드
    public static void main(String[] args) {
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() +  " " + arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob);     // 구독하고 싶다면 등록 할 수 있음 (옵저버 패턴의 장점)

        ExecutorService es = Executors.newSingleThreadExecutor ();
        es.execute(io);

        System.out.println(Thread.currentThread().getName() + " EXIT");
        es.shutdown();
        //io.run();;
    };
}
