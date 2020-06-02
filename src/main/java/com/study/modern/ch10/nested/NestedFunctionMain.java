package com.study.modern.ch10.nested;

import com.study.modern.ch10.model.Order;
import lombok.extern.slf4j.Slf4j;

import static com.study.modern.ch10.nested.NestedFunctionOrderBuilder.*;

@Slf4j
public class NestedFunctionMain {
    public static void main(String[] args) {
        Order order = order("BigBank",
            buy(80,
                stock("IBM", on("NYSE")),
                at(125.00)),
            sell(50,
                stock("GOOGLE", on("NASDAQ")),
                at(375.00))
        );

        log.info("value : {}", order.getValue());
    }
}
