package com.study.modern.ch06;

import com.study.modern.ch04.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.reducing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReducingTest {
    List<Dish> menu;
    @BeforeEach
    public void init() {
        menu = Dish.generateSample();
    }

    @Test
    public void reducingTest() {
        Optional<Dish> fatestMenu = menu.stream()
                .collect(reducing((a,b)-> a.getCalories()>b.getCalories()?a:b));
        assertTrue(fatestMenu.isPresent());
        fatestMenu.ifPresent(d->assertThat(800, is(d.getCalories())));
    }
}
