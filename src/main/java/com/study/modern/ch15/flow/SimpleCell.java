package com.study.modern.ch15.flow;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

@Slf4j
public class SimpleCell implements Flow.Processor<Integer, Integer> {

    private int value = 0;
    private String name;
    private List<Flow.Subscriber<? super Integer>> subscribers = new ArrayList<>();

    public SimpleCell(String name) {
        this.name = name;
    }

    // publisher
    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        subscribers.add(subscriber);
    }

    public void subscribe(Consumer<? super Integer> onNext) {
        subscribe(new Flow.Subscriber<>() {

            @Override
            public void onComplete() {}

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onNext(Integer val) {
                onNext.accept(val);
            }

            @Override
            public void onSubscribe(Flow.Subscription s) {}

        });
    }

    // subscriber
    @Override
    public void onNext(Integer newValue) {
        value = newValue;
        log.info("{} : {}", name, value);
        notifyAllSubscribers();
    }

    @Override
    public void onComplete() {
        log.info("Done");
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onSubscribe(Flow.Subscription s) {
    }

    private void notifyAllSubscribers() {
        subscribers.forEach(subscriber -> subscriber.onNext(value));
    }
}