package com.study.modern.ch16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static com.study.modern.ch16.Util.delay;

@Slf4j
public class ShopException {
    private final String product;

    public ShopException(String product) {
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
        throw new RuntimeException("product : " + product);
    }

    public static void main(String[] args) {
        ShopException shopSync = new ShopException("BestShop");
        Future<Double> futurePrice = shopSync.getPriceAsync("my favorite product");
        try {
            futurePrice.get(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
