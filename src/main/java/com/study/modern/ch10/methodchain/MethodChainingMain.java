package com.study.modern.ch10.methodchain;

import com.study.modern.ch10.model.Order;
import lombok.extern.slf4j.Slf4j;

import static com.study.modern.ch10.methodchain.MethodChainingOrderBuilder.forCustomer;

@Slf4j
public class MethodChainingMain {
    public static void main(String[] args) {
        Order order = forCustomer( "BigBank" )
            .buy( 80 )
                .stock( "IBM" )
                .on( "NYSE" )
                .at( 125.00 )
            .sell( 50 )
                .stock( "GOOGLE" )
                .on( "NASDAQ" )
                .at( 375.00 )
            .end();
        log.info("value : {}", order.getValue());
    }
}
