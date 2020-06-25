package com.study.modern.ch16.compose;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class BestDiscountPriceFinder {
    List<Shop> shops;
    private final Executor executor;


    BestDiscountPriceFinder() {
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
            .map(shop -> shop.getPrice(product))
            .map(Quote::parse)
            .map(Discount::applyDiscount)
            .collect(toList());
    }



    public List<String> findPricesWithDiscount(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> shop.getPrice(product), executor)
            )
            .map(future -> future.thenApplyAsync(Quote::parse))
            .map(future -> future.thenCompose(quote ->
                    CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)
                )
            )
            .collect(toList());
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
    }

    public static void main(String[] args) {
        log.info("=== Discount call");
        long start = System.nanoTime();
//        log.info("{}", new BestDiscountPriceFinder().findPrices("product"));
        long duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);

        log.info("=== Completable Discount call");
        start = System.nanoTime();
        log.info("{}", new BestDiscountPriceFinder().findPricesWithDiscount("product"));
        duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);
    }
}
