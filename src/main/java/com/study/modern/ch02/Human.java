package com.study.modern.ch02;

import lombok.Getter;

@Getter
public class Human extends Animal {
    final int intelligence;

    public Human(int age, int intelligence) {
        super(age);
        this.intelligence = intelligence;
    }
}
