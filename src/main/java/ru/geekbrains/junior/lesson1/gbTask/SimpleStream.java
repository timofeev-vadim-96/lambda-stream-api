package ru.geekbrains.junior.lesson1.gbTask;

import java.util.stream.Stream;

public class SimpleStream {
    public static void main(String[] args) {
        System.out.println(Stream.of(10, 22, 31, 15, 8)
                .filter(n -> n % 2 == 0)
                .mapToDouble(Integer::doubleValue)
                .average().getAsDouble());
    }
}
