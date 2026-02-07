package ru.kuzmich;

import java.lang.reflect.Array;

public class FilterUtil {

    public static <T> T[] filter(T[] array, Filter<T> filter) {
        if (array == null || filter == null) {
            throw new IllegalArgumentException("Array and filter cannot be null");
        }
        T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                result[i] = filter.apply(array[i]);
            } else {
                result[i] = null;
            }
        }
        return result;
    }
}
