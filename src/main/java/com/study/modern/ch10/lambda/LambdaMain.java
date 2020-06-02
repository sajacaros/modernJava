package com.study.modern.ch10.lambda;

import com.study.modern.ch10.model.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LambdaMain {
    public static void main(String[] args) {
        Order order = LambdaOrderBuilder.order( o -> {
            o.forCustomer( "BigBank" );
            o.buy( t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock(s -> {
                    s.symbol("IBM");
                    s.market("NYSE");
                });
            });
            o.sell( t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock(s -> {
                    s.symbol("GOOGLE");
                    s.market("NASDAQ");
                });
            });
        });

        log.info("value : {}", order.getValue());
    }
}
