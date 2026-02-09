package ru.kuzmich;

import java.util.HashMap;
import java.util.Map;

public class FrequencyCounter {

    public static <T> Map<T, Integer> countFrequency(T[] array) {
        Map<T, Integer> frequencyMap = new HashMap<>();

        if (array == null) {
            return frequencyMap;
        }

        for (T element : array) {
            if (element != null) {
                frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
            }
        }

        return frequencyMap;
    }
}
