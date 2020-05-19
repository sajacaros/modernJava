package com.study.modern.ch06;

import com.study.modern.ch04.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.stream.Collectors.*;

public class GroupByTest {
    Map<String, List<String>> dishTags;
    List<Dish> menu;
    Logger logger = LoggerFactory.getLogger(GroupByTest.class);

    @BeforeEach
    public void init() {
        dishTags = new HashMap<>();
        dishTags.put("pork", Arrays.asList("greasy", "salty"));
        dishTags.put("beef", Arrays.asList("salty", "roasted"));
        dishTags.put("chicken", Arrays.asList("fried", "crisp"));
        dishTags.put("french fries", Arrays.asList("greasy", "fried"));
        dishTags.put("rice", Arrays.asList("light", "natural"));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
        dishTags.put("pizza", Arrays.asList("tasty", "salty"));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh"));

        menu = Dish.generateSample();
    }


    @Test
    public void 요리타입별요리이름() {
        Map<Dish.Type, List<String>> dishNamesByType =
            menu.stream()
                .collect(
                    groupingBy(
                        Dish::getType,
                        mapping(Dish::getName, toList())
                    )
                );
        logger.info("{}",dishNamesByType);
    }

    @Test
    public void 요리타입별태그목록() {
        Map<Dish.Type, Set<String>> dishNamesByType =
            menu.stream()
                .collect(
                    groupingBy(
                        Dish::getType,
                        flatMapping(dish -> dishTags.get( dish.getName() ).stream(), toSet())
                    )
                );
        logger.info("{}",dishNamesByType);
    }
}
