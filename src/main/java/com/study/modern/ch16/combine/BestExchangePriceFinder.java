package com.study.modern.ch16.combine;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class BestExchangePriceFinder {
    List<Shop> shops;
    private final Executor executor;


    BestExchangePriceFinder() {
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

    public List<Double> findPricesWithDiscount(String product) {
        List<CompletableFuture<Double>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> shop.getPrice(product), executor)
            ).map(future -> future.thenApply(Quote::parse))
            .map(future -> future.thenCompose(quote ->
                    CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)
                )
            )
            .map(future -> future.thenCombine(
                CompletableFuture.supplyAsync(()-> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                (price, rate) -> price * rate
                )
            )
            .collect(toList());
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
    }

    public static void main(String[] args) {
        log.info("=== Combine call");
        long start = System.nanoTime();
        log.info("{}", new BestExchangePriceFinder().findPricesWithDiscount("product"));
        long duration = (System.nanoTime() - start) / 1000000;
        log.info("Done in {} msecs", duration);
    }
}
