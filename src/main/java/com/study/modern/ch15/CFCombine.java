package com.study.modern.ch15;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CFCombine {
    public static void main(String[] args)
        throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        log.info("start");
        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z)-> y + z);
        executorService.submit(() -> a.complete(f(x)));
        executorService.submit(() -> b.complete(g(x)));
        log.info("complete : {}", c.get());
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
