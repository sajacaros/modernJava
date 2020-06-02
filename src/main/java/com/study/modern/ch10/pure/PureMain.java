package com.study.modern.ch10.pure;

import com.study.modern.ch10.model.Order;
import com.study.modern.ch10.model.Stock;
import com.study.modern.ch10.model.Trade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PureMain {
    public static void main(String[] args) {
        Order order = new Order();
        order.setCustomer("BigBank");

        Trade trade1 = new Trade();
        trade1.setType(Trade.Type.BUY);

        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order.addTrade(trade1);

        Trade trade2 = new Trade();
        trade2.setType(Trade.Type.BUY);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order.addTrade(trade2);

        log.info("value : {}", order.getValue());
    }
}
