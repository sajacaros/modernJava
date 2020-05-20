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
    public void intersection1EmptySetList(){ // boundary
        Set<String> test = intersection1(Arrays.asList(Collections.emptySet()));
        assertTrue(test.isEmpty());
    }

    @Test
    public void intersection1EmptyList(){ // boundary
        Set<String> test = intersection1(Collections.emptyList());
        assertTrue(test.isEmpty());
    }

    @Test
    public void intersection2Test(){
        Set<String> test = intersection2(setList);
        assertEquals(1, test.size());
        assertTrue(test.contains("3"));
    }

    @Test
    public void intersection2EmptySetList(){ // boundary
        Set<String> test = intersection2(Arrays.asList(Collections.emptySet()));
        assertTrue(test.isEmpty());
    }

    @Test
    public void intersection2EmptyList(){ // boundary
        Set<String> test = intersection2(Collections.emptyList());
        assertTrue(test.isEmpty());
    }

    @Test
    public void intersection3Test(){
        Set<String> test = intersection3(setList);
        assertEquals(1, test.size());
        assertTrue(test.contains("3"));
    }

    @Test
    public void intersection3EmptySetList(){ // boundary
        Set<String> test = intersection3(Arrays.asList(Collections.emptySet()));
        assertTrue(test.isEmpty());
    }

    @Test
    public void intersection3EmptyList(){ // boundary
        Set<String> test = intersection3(Collections.emptyList());
        assertTrue(test.isEmpty());
    }

    private Set<String> intersection1(List<Set<String>> setList) {
        return setList.stream()
            .reduce((a, b) -> a.stream()
                .filter(element -> b.contains(element))
                .collect(Collectors.toSet()))
            .orElse(Collections.emptySet());
    }

    private Set<String> intersection2(List<Set<String>> setList) {
        return setList.stream()
            .findFirst()
            .map(first->setList.stream()
                .skip(1)
                .collect(()->new HashSet(first), Set::retainAll, Set::retainAll))
            .orElseGet(HashSet::new);
    }

    private Set<String> intersection3(List<Set<String>> setList) {
        if(setList.isEmpty() ) {
            return Collections.emptySet();
        }
        Set<String> first = setList.get(0);
        return setList.stream()
                .skip(1)
                .collect(()->new HashSet(first), Set::retainAll, Set::retainAll);
    }
}
