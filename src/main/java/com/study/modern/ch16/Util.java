package com.study.modern.ch16;

public class Util {
    public static void delay() {
        delay(5000);
    }

    public static void delay(long n) {
        try{
            Thread.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException("occurred interrupt", e);
        }
    }
}
