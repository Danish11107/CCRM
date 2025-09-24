package edu.ccrm.util;

/**
 * Simple assertions demonstration.
 */
public class AssertDemo {
    public static void demo(){
        int x = 5;
        assert x > 0 : "x must be positive";
        System.out.println("Assertion passed: x=" + x);
    }
}
