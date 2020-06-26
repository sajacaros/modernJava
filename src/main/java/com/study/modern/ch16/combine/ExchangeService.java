package com.study.modern.ch16.combine;

import lombok.extern.slf4j.Slf4j;

import static com.study.modern.ch16.Util.delay;

@Slf4j
public class ExchangeService {
    public static final double DEFAULT_RATE = 1.35;

    public enum Money {
        USD(1.0), EUR(1.35387), GBP(1.69715), CAD(.92106), MXN(.07683);

        private final double rate;

        Money(double rate) {
            this.rate = rate;
        }
    }

    public static double getRate(Money source, Money destination) {
        log.info("getRate start");
        double dummy =  getRateWithDelay(source, destination);
        log.info("getRate end");
        return dummy;
    }

    private static double getRateWithDelay(Money source, Money destination) {
        delay();
        return destination.rate / source.rate;
    }
}
