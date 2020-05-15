package com.study.modern.ch03;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FruitMain {

    static Fruit createFruit(Fruit.FruitType fruitType, int weight) {
        return fruitType.apply(weight);
    }

    public static void main(String[] args) {
        Fruit apple = createFruit(Fruit.FruitType.APPLE, 150);
        log.info(apple.toString());
        Fruit orange = createFruit(Fruit.FruitType.ORANGE, 200);
        log.info(orange.toString());
    }
}
