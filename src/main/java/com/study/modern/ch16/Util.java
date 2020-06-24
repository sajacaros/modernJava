package com.study.modern.ch16;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class Util {
    private static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
    private static final Random random = new Random();


    public static void delay() {
        delay(3000);
    }

    private static void delay(long n) {
        try{
            Thread.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException("occurred interrupt", e);
        }
    }

    public static void delayRandom() {
        int delay = 500 + random.nextInt(2000);
        try{
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException("occurred interrupt", e);
        }
    }

    public static double format(double number) {
        synchronized (formatter) {
            return Double.valueOf(formatter.format(number));
        }
    }
}
