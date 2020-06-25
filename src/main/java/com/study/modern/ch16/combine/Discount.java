package com.study.modern.ch16.combine;

import static com.study.modern.ch16.Util.delay;
import static com.study.modern.ch16.Util.format;

//@Slf4j
public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static Double applyDiscount(Quote quote) {
//        log.info("applyDiscount start");
        double dummy = apply(quote.getPrice(), quote.getDiscountCode());
//        log.info("applyDiscount end");
        return dummy;
    }

    private static double apply(double price, Code code) {
        delay();
        return format(price * (100 - code.percentage) / 100);
    }
}
