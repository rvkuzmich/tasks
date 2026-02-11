package ru.kuzmich;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<BigInteger> {

    private final int start;
    private final int end;
    private static final int THRESHOLD = 5;

    public FactorialTask(int n) {
        this(1, n);
    }

    private FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected BigInteger compute() {
        int length = end - start + 1;

        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        int middle = start + length / 2;
        FactorialTask leftTask = new FactorialTask(start, middle - 1);
        FactorialTask rightTask = new FactorialTask(middle, end);

        leftTask.fork();
        rightTask.fork();

        BigInteger leftResult = leftTask.join();
        BigInteger rightResult = rightTask.join();

        return leftResult.multiply(rightResult);
    }

    private BigInteger computeSequentially() {
        BigInteger result = BigInteger.ONE;

        for(int i = start; i <= end; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
