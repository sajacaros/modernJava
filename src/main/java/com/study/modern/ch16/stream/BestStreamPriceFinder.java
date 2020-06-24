package com.study.modern.ch16.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class BestStreamPriceFinder {
    List<Shop> shops;
    private final Executor executor;


    BestStreamPriceFinder() {
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

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> shop.getPrice(product), executor)
            )
            .map(future -> future.thenApply(Quote::parse))
            .map(future -> future.thenCompose(quote ->
                CompletableFuture.supplyAsync(
                    () -> Discount.applyDiscount(quote), executor)));
    }

    public static void main(String[] args) {
        CompletableFuture[] futures = new BestStreamPriceFinder().findPricesStream("product")
            .map(f -> f.thenAccept(log::info))
            .toArray(size -> new CompletableFuture[size]);

        log.info("join");

        CompletableFuture.allOf(futures).join();
//        CompletableFuture.anyOf(futures).join();
    }
}
