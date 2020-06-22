package com.study.modern.ch16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static com.study.modern.ch16.Util.delay;

@Slf4j
public class ShopSupplyAsync {
    private final String product;

    public ShopSupplyAsync(String product) {
        this.product = product;
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(()->calculatorPrice(product));
    }

    private double calculatorPrice(String product) {
        delay();
        throw new RuntimeException("product : " + product);
    }

    public static void main(String[] args) {
        ShopSupplyAsync shopSync = new ShopSupplyAsync("BestShop");
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
