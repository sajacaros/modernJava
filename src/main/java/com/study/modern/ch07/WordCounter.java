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
    private final List<Character> word = new ArrayList();

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCounter(int counter, boolean lastSpace, List<Character> word) {
        this.counter = counter;
        this.lastSpace = lastSpace;
        this.word.addAll(word);
    }

    public WordCounter(int counter, boolean lastSpace, Character c) {
        this.counter = counter;
        this.lastSpace = lastSpace;
        this.word.add(c);
    }
    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ?
                this :
                new WordCounter(counter, true);
        } else {
            if(lastSpace) {
                return new WordCounter(counter+1, false, c);
            } else {
                word.add(c);
                return this;
            }
        }
    }
    public WordCounter combine(WordCounter wordCounter) {
        int left = this.word.isEmpty()?0:this.counter;
        int right = wordCounter.word.isEmpty()?0:wordCounter.counter;
        log.info("left : {}, word: {}, right: {}, word: {}", left, this.word, right, wordCounter.word);
        List<Character> words = new ArrayList(this.word);
        words.addAll(wordCounter.word);
        return new WordCounter(left+right,
            wordCounter.lastSpace, words);
    }
    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        final String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita " +
                "mi  ritrovai in una  selva oscura" +
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
