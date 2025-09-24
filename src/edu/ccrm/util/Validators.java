package edu.ccrm.util;

import java.util.regex.*;

/**
 * Simple validators utility.
 */
public class Validators {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    public static boolean isEmail(String s){ return s!=null && EMAIL.matcher(s).matches(); }
}
