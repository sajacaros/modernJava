package com.study.modern.ch03;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.function.IntFunction;

public interface Fruit {
    enum Type {
        APPLE(Apple::new), ORANGE(Orange::new);

        IntFunction<Fruit> fruitFunction;
        Type(IntFunction<Fruit> fruitFunction) {
            this.fruitFunction = fruitFunction;
        }
    }

    static Fruit createFruit(Fruit.Type fruitType, int weight) {
        return fruitType.fruitFunction.apply(weight);
    }

    @ToString
    class Apple implements Fruit{
        private final int weight;
        Apple(int weight) {
            this.weight = weight;
        }
    }

    @ToString
    @Slf4j
    class Orange implements Fruit{
        private final int weight;
        Orange(int weight) {
            this.weight = weight;
        }
    }
}
