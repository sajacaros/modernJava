package com.study.modern.ch03;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Fruit f = Fruit.createFruit(Fruit.Type.APPLE, 150);
        log.info(f.toString());
        Fruit f2 = Fruit.createFruit(Fruit.Type.ORANGE, 200);
        log.info(f2.toString());
    }
}
