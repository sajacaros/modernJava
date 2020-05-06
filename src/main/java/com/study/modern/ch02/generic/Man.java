package com.study.modern.ch02.generic;

import lombok.Getter;

@Getter
public class Man extends Human{
    final int power;

    public Man(int age, int intelligence, int power) {
        super(age, intelligence);
        this.power = power;
    }
}
