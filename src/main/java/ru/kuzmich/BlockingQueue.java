package ru.kuzmich;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {

    private final Queue<T> queue;
    private final int maxSize;

    public BlockingQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    public synchronized void enqueue(T item) throws InterruptedException{
        while (queue.size() == maxSize) {
            wait();
        }

        queue.offer(item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        T item = queue.poll();
        notifyAll();

        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}
