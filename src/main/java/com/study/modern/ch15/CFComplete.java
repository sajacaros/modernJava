package com.study.modern.ch15;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CFComplete {
    public static void main(String[] args)
        throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        int x = 1337;

        log.info("start");
        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(f(x)));
        int b = g(x);
        log.info("complete : {}", a.get() + b);
        executorService.shutdown();
    }

    private static int f(int x) throws InterruptedException {
        Thread.sleep(10000);
        log.info("fffff");
        return 1;
    }

    private static Integer g(int x) throws InterruptedException {
        Thread.sleep(5000);
        log.info("ggggg");
        return 2;
    }
}
