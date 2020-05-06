package com.study.modern.ch02.generic;

import lombok.Getter;


@Getter
public class Woman extends Human {
    final int beauty;

    public Woman(int age, int intelligence, int beauty) {
        super(age, intelligence);
        this.beauty = beauty;
    }
}
