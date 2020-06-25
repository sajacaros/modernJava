package com.study.modern.ch16.combine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static com.study.modern.ch16.Util.delay;
import static com.study.modern.ch16.Util.format;

@Slf4j
public class Shop {
    @Getter
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        log.info("getPrice start");
        double price = calculatePrice(product);
        log.info("getPrice end");
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    public double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }
}
