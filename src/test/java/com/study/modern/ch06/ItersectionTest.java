package com.study.modern.ch06;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItersectionTest {
    List<Set<String>> setList;

    @BeforeEach
    public void init() {
        setList = Arrays.asList(
                Set.of("1", "2", "3"),
                Set.of("2", "3", "4"),
                Set.of("3", "5", "7"));
    }

    @Test
    public void intersection1Test(){
        Set<String> test = intersection1(setList);
        assertEquals(1, test.size());
        assertTrue(test.contains("3"));
    }

    @Test
    public void intersection1Empty(){ // boundary
        Set<String> test = intersection1(Arrays.asList(Collections.emptySet()));
        assertTrue(test.isEmpty());
    }

    @Test
    public void intersection2Test(){
        Set<String> test = intersection2(setList);
        assertEquals(1, test.size());
        assertTrue(test.contains("3"));
    }

    @Test
    public void intersection2Empty(){ // boundary
        Set<String> test = intersection2(Arrays.asList(Collections.emptySet()));
        assertTrue(test.isEmpty());
    }

    private Set<String> intersection1(List<Set<String>> setList) {
        return setList.stream()
            .reduce((a, b) -> a.stream()
                .filter(ele -> b.contains(ele))
                .collect(Collectors.toSet()))
            .orElse(Collections.emptySet());
    }

    private Set<String> intersection2(List<Set<String>> setList) {

        return setList.stream()
            .findFirst()
            .map(HashSet::new)
            .map(first->setList.stream()
                .skip(1)
                .collect(()->first, Set::retainAll, Set::retainAll))
            .orElseGet(HashSet::new);
    }
}
