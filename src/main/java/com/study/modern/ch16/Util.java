package com.study.modern.ch16;

public class Util {
    public static void delay() {
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException("occurred interrupt", e);
        }
    }
}
