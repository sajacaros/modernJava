package com.study.modern.ch05;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pythagorean {
    public static Stream<int[]> stupidPythagoreanTriples(int start, int endExclusive) {
        return IntStream.rangeClosed(start, endExclusive).boxed()
            .flatMap(a ->
                IntStream.rangeClosed(a, endExclusive)
                    .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                    .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
            );
    }

    public static Stream<double[]> goodPythagoreanTriples(int start, int endExclusive) {
        return IntStream.rangeClosed(start, endExclusive).boxed()
            .flatMap(a ->
                IntStream.rangeClosed(a, endExclusive)
                    .mapToObj(b -> new double[]{a, b,Math.sqrt(a*a+b*b)})
                    .filter(t -> t[2] % 1 == 0)
            );
    }

    public static void main(String[] args) {
        Executors.newSingleThreadExecutor().execute(()->
            stupidPythagoreanTriples(1,10000000)
                .limit(1000)
                .forEach(t->System.out.println("stupid : " + t[0] + ", " + t[1]+", "+t[2]))
        );

        Executors.newSingleThreadExecutor().execute(()->
            goodPythagoreanTriples(1,10000000)
                .limit(1000)
                .forEach(t->System.out.println("good : " + t[0] + ", " + t[1]+", "+t[2]))
        );
    }
}
