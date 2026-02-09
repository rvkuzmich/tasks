package ru.kuzmich;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> taskQueue = new BlockingQueue<>(10);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    final int taskId = i;
                    Runnable task = () -> {
                        System.out.printf("Task is in progress %s in thread %s\n", taskId, Thread.currentThread().getId());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    };

                    System.out.printf("Offering task %s to queue. Queue size is %s\n", taskId, taskQueue.size());
                    taskQueue.enqueue(task);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Runnable task = taskQueue.dequeue();
                    System.out.printf("Task is polled. Queue size is %s\n", taskQueue.size());
                    executorService.execute(task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        Thread.sleep(5000);

        producer.interrupt();
        consumer.interrupt();

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
