package com.study.modern.ch16;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.study.modern.ch16.Util.delay;

@Slf4j
public class ShopAsync {
    private final String product;
    Random random = new Random();

    public ShopAsync(String product) {
        this.product = product;
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatorPrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    private double calculatorPrice(String product) {
        delay();
        return random.nextDouble() + product.charAt(0) + product.charAt(1);
    }

    public static void main(String[] args) {
        ShopAsync shop = new ShopAsync("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        log.info("Invocation returned after " + invocationTime + " msecs");
// Do some more tasks, like querying other shops
//        doSomethingElse();
// while the price of the product is being calculated
        try {
            double price = futurePrice.get();
            log.info("Price is {}", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        log.info("Price returned after " + retrievalTime + " msecs");


    }
}
