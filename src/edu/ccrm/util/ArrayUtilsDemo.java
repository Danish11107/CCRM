package edu.ccrm.util;

import java.util.Arrays;

/**
 * Demonstrates arrays, sorting, binary search and basic String utilities.
 */
public class ArrayUtilsDemo {
    public static void demo(){
        int[] nums = {5,2,9,1,3};
        System.out.println("Before: " + Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println("After sort: " + Arrays.toString(nums));
        int idx = Arrays.binarySearch(nums, 3);
        System.out.println("Index of 3: " + idx);

        String s = "one,two,three";
        String[] parts = s.split(",");
        System.out.println("Parts length: " + parts.length);
        System.out.println("Join: " + String.join("-", parts));
    }
}
