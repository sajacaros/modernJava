package com.study.modern.ch02.generic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UpperBoundTest {
    public static class ObjectComparator implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            return o1.hashCode() - o2.hashCode();
        }
    }

    public static class HumanComparator implements Comparator<Human> {
        @Override
        public int compare(Human h1, Human h2) {
            return h1.getIntelligence()-h2.getIntelligence();
        }
    }

    public static class AnimalComparator implements Comparator<Animal> {
        @Override
        public int compare(Animal a1, Animal a2) {
            return a1.getAge() - a2.getAge();
        }
    }

    public static class ManComparator implements Comparator<Man> {
        @Override
        public int compare(Man a1, Man a2) {
            return a1.getPower() - a2.getPower();
        }
    }

    public static void main(String[] args) {

        List<Human> humanList0 = new ArrayList(List.of(new Human(10,100),new Human(21,98),new Human(30,99)));
        humanList0.sort(new ObjectComparator());

        List<Human> humanList2 = new ArrayList(List.of(new Human(10,100),new Human(21,98),new Human(30,99)));
        humanList2.sort(new AnimalComparator());

        List<Human> humanList1 = new ArrayList(List.of(new Human(10,100),new Human(21,98),new Human(30,99)));
        humanList1.sort(new HumanComparator());

        List<Human> humanList3 = new ArrayList(List.of(new Human(10,100),new Human(21,98),new Human(30,99)));
//        humanList3.sort(new ManComparator());
    }
}
