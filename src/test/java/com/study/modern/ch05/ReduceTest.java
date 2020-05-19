package com.study.modern.ch05;

import com.study.modern.ch04.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ReduceTest {
    List<Integer> numbers;
    @BeforeEach
    public void init() {
        numbers = IntStream.rangeClosed(1,1000).boxed().collect(toList());
    }

    @Test
    public void sum1() {
        Optional<Integer> reduce = numbers.stream()
                .reduce((n1, n2) -> n1 + n2);
        System.out.println(reduce.get());
    }

    @Test
    public void sum2() {
        Integer reduce = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println(reduce);
    }
}
