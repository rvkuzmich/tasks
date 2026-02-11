package ru.kuzmich;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        int n = 10; // Вычисление факториала для числа 10

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);

        long startTime = System.currentTimeMillis();
        BigInteger result = forkJoinPool.invoke(factorialTask);
        long endTime = System.currentTimeMillis();

        System.out.println("Факториал " + n + "! = " + result);
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }
}
