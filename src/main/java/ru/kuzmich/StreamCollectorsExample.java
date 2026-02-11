package ru.kuzmich;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollectorsExample {

    public static void main(String[] args) {
        List<Order> orders = List.of(
            new Order("Laptop", 1200.0),
            new Order("Smartphone", 800.0),
            new Order("Laptop", 1500.0),
            new Order("Tablet", 500.0),
            new Order("Smartphone", 900.0)
        );

        Map<String, Double> productTotalCost = orders.stream().collect(
            Collectors.groupingBy(Order::getProduct, Collectors.summingDouble(Order::getCost)));

        List<Map.Entry<String, Double>> topThree = productTotalCost.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(3).toList();

        System.out.println("Top three order cost products:");
        System.out.println();

        topThree.forEach(entry -> System.out.printf("Product: %s, cost: %s\n",
            entry.getKey(), entry.getValue()));

    }
}