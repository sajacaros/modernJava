package com.study.modern.ch10.mixed;

import com.study.modern.ch10.model.Order;
import lombok.extern.slf4j.Slf4j;

import static com.study.modern.ch10.mixed.MixedOrderBuilder.*;

@Slf4j
public class MixedMain {
    public static void main(String[] args) {
        Order order =
            forCustomer("BigBank",
                buy(t -> t.quantity(80)
                    .stock("IBM")
                    .on("NYSE")
                    .at(125.00)),
                sell(t -> t.quantity(50)
                    .stock("GOOGLE")
                    .on("NASDAQ")
                    .at(375.00)));
        log.info("value : {}", order.getValue());
    }
}
