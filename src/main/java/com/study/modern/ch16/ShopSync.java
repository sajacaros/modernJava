package com.study.modern.ch16;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static com.study.modern.ch16.Util.delay;

@Slf4j
public class ShopSync {
    private final String product;
    Random random = new Random();

    public ShopSync(String product) {
        this.product = product;
    }

    public double getPrice(String product) {
        return calculatorPrice(product);
    }

    private double calculatorPrice(String product) {
        delay();
        return random.nextDouble() + product.charAt(0) + product.charAt(1);
    }

    public static void main(String[] args) {
        ShopSync shopSync = new ShopSync("BestShop");
        long start = System.nanoTime();

        Double price = shopSync.getPrice("my favorite product");

        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        log.info("Invocation returned after " + invocationTime + " msecs");

        log.info("Price is {}", price);

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        log.info("Price returned after " + retrievalTime + " msecs");
    }
}
