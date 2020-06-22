package com.study.modern.ch16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(()-> 1.0);
        try {
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException ee) {
            // the computation threw an exception
        } catch (InterruptedException ie) {
            // the current thread was interrupted while waiting
        } catch (TimeoutException te) {
            // the timeout expired before the Future completion
        }
    }

}
