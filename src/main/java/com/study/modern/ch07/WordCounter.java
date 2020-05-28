package com.study.modern.ch07;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ?
                this :
                new WordCounter(counter, true);
        } else {
            return lastSpace ?
                new WordCounter(counter+1, false):
                this;
        }
    }
    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(this.counter + wordCounter.counter,
            wordCounter.lastSpace);
    }
    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        final String SENTENCE =
            " Nel   mezzo del cammin   di nostra  vita " +
                "mi  ritrovai in una    selva oscura" +
                " ché la  dritta via era   smarrita ";
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt);
        System.out.println("Found " + countWords(stream.parallel()) + " words");
    }

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
            WordCounter::accumulate,
            WordCounter::combine);
        return wordCounter.getCounter();
    }
}
