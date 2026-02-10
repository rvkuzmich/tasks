package ru.kuzmich;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTaskExecutor {

    private final int numberOfTasks;
    private final CyclicBarrier cyclicBarrier;
    private final List<ComplexTask> tasks;
    private final List<Integer> results;
    private final AtomicInteger completedTasks;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        this.cyclicBarrier = new CyclicBarrier(numberOfTasks, this::combineResults);
        this.tasks = new ArrayList<>();
        this.results = new ArrayList<>();
        this.completedTasks = new AtomicInteger(0);

        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(new ComplexTask(i + 1));
        }
    }

    private void combineResults() {
        System.out.println("%nAll tasks completed, combining results%n");

        int totalResult = 0;

        for (ComplexTask task : tasks) {
            totalResult += task.getResult();
            results.add(task.getResult());
        }
        System.out.printf("%nTasks results: %s%n", results);
        System.out.printf("Combined result: %s%n", totalResult);
    }

    public void executeTasks(int numberOfTasks) {
        if (numberOfTasks != this.numberOfTasks) {
            System.out.printf(
                "%nWarning: incoming number of tasks (%s) doesn't match with expected (%s)%n",
                numberOfTasks, this.numberOfTasks);
        }

        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(numberOfTasks);
            System.out.printf("%n%s: Begin execution of %s tasks%n%n",
                Thread.currentThread().getName(), numberOfTasks);

            for (int i = 0; i < numberOfTasks; i++) {
                final int taskIndex = i;
                executorService.submit(() -> {
                    try {
                        ComplexTask task = tasks.get(taskIndex);
                        task.execute();

                        int completed = completedTasks.incrementAndGet();
                        System.out.printf("%s: completed tasks: %s/%s%n",
                            Thread.currentThread().getName(), completed, numberOfTasks);

                        System.out.printf("%s: get to barrier, waiting...%n",
                            Thread.currentThread().getName());
                        cyclicBarrier.await();

                        System.out.printf("%s: continue after barrier%n",
                            Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.printf("%n%s has been interrupted%n",
                            Thread.currentThread().getName());
                    } catch (BrokenBarrierException e) {
                        System.out.printf("%n%s: barrier is broken:%n%s",
                            Thread.currentThread().getName(), e.getMessage());
                    }
                });
            }
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }
}
