package ru.kuzmich;

public class Main {

    public static void main(String[] args) {
        Filter<String> toUpperCase = new Filter<String>() {
            @Override
            public String apply(String o) {
                return o != null ? o.toUpperCase() : null;
            }
        };

        String[] strings = {"apple", "Banana", null, "pEach"};
        String[] filteredStrings = FilterUtil.filter(strings, toUpperCase);

        for (String s : filteredStrings) {
            System.out.println(s);
        }
        System.out.println();

        Filter<Integer> addFive = new Filter<Integer>() {
            @Override
            public Integer apply(Integer o) {
                return o != null ? o + 5 : null;
            }
        };

        Integer[] nums = {1, 3, null, 7};
        Integer[] filteredNums = FilterUtil.filter(nums, addFive);

        for (Integer num : filteredNums) {
            System.out.println(num);
        }
    }
}
