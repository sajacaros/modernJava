package com.study.modern.ch05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class ExerciseTest {
    List<Transaction> transactions;

    @BeforeEach
    public void init() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );
    }

    //    1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리
    @Test
    public void transactions_asc_by_2011() {
        List<Transaction> result = transactions.stream()
            .filter(t -> t.getYear() == 2011)
            .sorted(Comparator.comparingInt(Transaction::getValue))
            .collect(Collectors.toList());

        assertThat(2, is(result.size()));
        Transaction previousTransaction = result.get(0);
        for(Transaction currentTransaction: result) {
            assertTrue(previousTransaction.getValue() <= currentTransaction.getValue());
            previousTransaction = currentTransaction;
        }

    }

    //    2. 거래자가 근무하는 모든 도시를 중복 없이 나열
    @Test
    public void city_of_trader() {
        List<String> cities = transactions.stream()
            .map(t->t.getTrader().getCity())
            .distinct()
            .collect(Collectors.toList());

        assertThat(2, is(cities.size()));
    }

    //    3. 케임브리지에서 근무하는 모든 거래자를 찾아 이름순으로 정렬
    @Test
    public void trader_in_Cambridge() {
        List<Trader> traders = transactions.stream()
            .map(Transaction::getTrader)
            .filter(trader -> trader.getCity().equals("Cambridge"))
            .distinct()
            .sorted(Comparator.comparing(Trader::getName))
            .collect(Collectors.toList());

        assertThat(3, is(traders.size()));
        assertThat("Alan", is(traders.get(0).getName()));
        assertThat("Brian", is(traders.get(1).getName()));
        assertThat("Raoul", is(traders.get(2).getName()));
    }

    //    4. 모든 거래자의 이름을 알파벳순으로 정렬
    @Test
    public void names_of_trader_with_sort() {
        String namesString = transactions.stream()
            .map(t->t.getTrader().getName())
            .distinct()
            .sorted()
            .reduce("", (n1,n2)->n1+" "+n2);

        assertThat(" Alan Brian Mario Raoul", is(namesString));
    }

    @Test
    public void names_of_trader_with_sort2() {
        String namesString = transactions.stream()
            .map(t->t.getTrader().getName())
            .distinct()
            .sorted()
            .collect(Collectors.joining(" "));

        assertThat("Alan Brian Mario Raoul", is(namesString));
    }

    //    5. 밀라노에 거래자가 있는가?
    @Test
    public void is_in_Milano() {
        boolean isInMilano = transactions.stream()
            .anyMatch(t->t.getTrader().getCity().equals("Milano"));
        assertFalse(isInMilano);
    }

    //    6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력
    @Test
    public void values_in_Cambridge() {
        List<Integer>values = transactions.stream()
            .filter(t->t.getTrader().getCity().equals("Cambridge"))
            .map(Transaction::getValue)
            .collect(Collectors.toList());
        assertThat(4, is(values.size()));
    }

    //    7. 전체 트랜잭션 중 최댓값
    @Test
    public void max_for_transaction() {
        int max = transactions.stream()
            .map(Transaction::getValue)
            .max(Integer::compareTo)
            .orElse(0);
        assertThat(1000, is(max));
    }

    @Test
    public void max_for_transaction2() {
        int max = transactions.stream()
            .map(Transaction::getValue)
            .reduce(0, (a,b)->a>b?a:b);
        assertThat(1000, is(max));
    }

    //    8. 전체 트랜잭션 중 최소값
    @Test
    public void min_for_transaction() {
        int min = transactions.stream()
            .map(Transaction::getValue)
            .min(Integer::compareTo)
            .orElse(0);
        assertThat(300, is(min));
    }

    @Test
    public void min_for_transaction2() {
        OptionalInt min = transactions.stream()
            .mapToInt(Transaction::getValue)
            .min();
        assertThat(300, is(min.getAsInt()));
    }
}
