package com.study.modern.ch03;

import lombok.ToString;

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
    class Orange implements Fruit{
        private final int weight;
        Orange(int weight) {
            this.weight = weight;
        }
    }

    static void main(String[] args) {
        Fruit f = Fruit.createFruit(Fruit.Type.APPLE, 150);
        System.out.println(f.toString());
        Fruit f2 = Fruit.createFruit(Fruit.Type.ORANGE, 200);
        System.out.println(f2.toString());
    }
}
