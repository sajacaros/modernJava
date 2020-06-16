package com.study.modern.ch15;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CFComplete {
    public static void main(String[] args)
        throws ExecutionException, InterruptedException {
        log.info("wait for jconsole");
        Thread.sleep(20000);
        ExecutorService executorService = new ThreadPoolExecutor(
            1,
            10,
            3,
             TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100)
        );

        int x = 1337;

        log.info("start");
        CompletableFuture<Integer> b = new CompletableFuture<>();
        executorService.submit(() -> b.complete(g(x)));
        int a = f(x);
        log.info("complete : {}", a + b.get());
        executorService.shutdown();

        Thread.sleep(30000);
    }

    private static Integer g(int x) throws InterruptedException {
        Thread.sleep(5000);
        log.info("ggggg");
        return 2;
    }

    private static int f(int x) throws InterruptedException {
        Thread.sleep(30000);
        log.info("fffff");
        return 1;
    }
}
