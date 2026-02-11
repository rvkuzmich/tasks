package ru.kuzmich;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParallelStreamCollectMapAdvancedExample {

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Student1", Map.of("Math", 90, "Physics", 85)),
            new Student("Student2", Map.of("Math", 95, "Physics", 88)),
            new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
            new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        System.out.println("Students list:");
        students.forEach(System.out::println);
        System.out.println();

        Map<String, Double> averageGrades = students.parallelStream()
            .flatMap(student -> student.getGrades().entrySet().stream())
            .collect(Collectors.groupingByConcurrent(Map.Entry::getKey, ConcurrentHashMap::new,
                Collectors.averagingDouble(Map.Entry::getValue)));

        System.out.println("Average student grades by subject:");

        averageGrades.entrySet().stream().sorted(Map.Entry.comparingByKey())
            .forEach(entry -> System.out.printf("%s: %.2f%n", entry.getKey(),
                entry.getValue()));
    }
}