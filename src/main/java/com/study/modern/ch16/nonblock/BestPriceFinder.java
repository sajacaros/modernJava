package com.study.modern.ch16.nonblock;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class BestPriceFinder {
    List<Shop> shops;
    private final Executor executor;


    BestPriceFinder() {
        shops = new ArrayList<>();
        IntStream.iterate(0, n -> n + 1)
            .takeWhile(n -> n < 10)
            .forEach(n -> shops.add(new Shop(n+"Alphabat")));
        executor =
            Executors.newFixedThreadPool(
                Math.min(shops.size(), 100),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
            );
    }

    public List<String> findPrices(String product) {
        return shops.stream()
            .map(shop ->
                String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
            .collect(toList());
    }

    public List<String> findPricesWithStream(String product) {
        return shops.parallelStream()
            .map(shop ->
                String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
            .collect(toList());
    }

    public List<String> findPricesWithCompletable(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
                )
            )
            .collect(toList());
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
    }

    public List<String> findPricesWithExecutor(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor
                )
            )
            .collect(toList());
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
    }

    public static void main(String[] args) {
        log.info("=== blocking call");
        long start = System.nanoTime();
        log.info("{}", new BestPriceFinder().findPrices("product"));
        long duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);

        log.info("=== parallelStream call");
        start = System.nanoTime();
        log.info("{}", new BestPriceFinder().findPricesWithStream("product"));
        duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);

        log.info("=== CompletableFuture call");
        start = System.nanoTime();
        log.info("{}", new BestPriceFinder().findPricesWithCompletable("product"));
        duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);

        log.info("=== CompletableFuture with executor call");
        start = System.nanoTime();
        log.info("{}", new BestPriceFinder().findPricesWithExecutor("product"));
        duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);
    }
}
