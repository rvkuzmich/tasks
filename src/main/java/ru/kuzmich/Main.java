package ru.kuzmich;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String[] nullStringArray = null;
        Map<String, Integer> stringIntegerMap = FrequencyCounter.countFrequency(nullStringArray);

        System.out.println(stringIntegerMap.isEmpty());

        System.out.println();

        String[] strings = {"apple", "banana", null, "apple", "orange", null, "banana", "apple",
            "peach", null};
        Map<String, Integer> stringFrequency = FrequencyCounter.countFrequency(strings);

        for (Map.Entry<String, Integer> entry : stringFrequency.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }

        System.out.println();

        Integer[] nums = {1, 2, 3, 1, 2, 4, 5, 4, 6, 3, 2, 5};
        Map<Integer, Integer> numFrequency = FrequencyCounter.countFrequency(nums);

        for (Map.Entry<Integer, Integer> entry : numFrequency.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
    }
}
