package ru.kuzmich;

public class ComplexTask {

    private final int taskId;
    private int result;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        System.out.printf("%s executing task %s%n", Thread.currentThread().getName(), taskId);

        try {
            Thread.sleep((long) (Math.random() * 1000) + 500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        result = (int) (Math.random() * 10);

        System.out.printf("%s completed task %s, result: %s%n", Thread.currentThread().getName(),
            taskId, result);
    }

    public int getResult() {
        return result;
    }
}
