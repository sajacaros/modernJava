package com.study.modern.ch16.nonblock;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

import static com.study.modern.ch16.Util.delay;

@Slf4j
public class Shop {
    private final String product;
    Random random = new Random();

    public Shop(String product) {
        this.product = product;
    }

    public double getPrice(String product) {
        return calculatorPrice(product);
    }

    private double calculatorPrice(String product) {
        delay(3000);
        return random.nextDouble() + product.charAt(0) + product.charAt(1);
    }

    public String getName() {
        return product;
    }
}
