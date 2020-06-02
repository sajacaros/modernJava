package com.study.modern.ch10.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
    private String customer;
    private List<Trade> trades = new ArrayList();

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public double getValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}
