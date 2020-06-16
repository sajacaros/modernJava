package com.study.modern.ch15;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        System.out.println("print 1");
        long start = System.currentTimeMillis();
        IntStream.range(0, 20).forEach(i -> {
            new Thread(()->{
                lock.lock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("——"+i);
                lock.unlock();
            }).start();
        });

        Thread.sleep(2000);
        System.out.println("print 2");
        System.out.println(format("takes %d millisecond", System.currentTimeMillis()-start));
    }
}
