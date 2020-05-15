package com.study.modern.ch03;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.IntFunction;

public interface Fruit {
    enum FruitType {
        APPLE(Apple::new), ORANGE(Orange::new);

        IntFunction<Fruit> fruitFunction;
        FruitType(IntFunction<Fruit> fruitFunction) {
            this.fruitFunction = fruitFunction;
        }

        Fruit apply(int weight) {
            return fruitFunction.apply(weight);
        }
    }

    @ToString
    @RequiredArgsConstructor
    class Apple implements Fruit{
        private final int weight;
    }

    @ToString
    @RequiredArgsConstructor
    class Orange implements Fruit{
        private final int weight;
    }
}
