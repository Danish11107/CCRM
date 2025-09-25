package edu.ccrm.util;

import java.io.File;

/**
 * Recursion utility examples.
 */
public class RecursionUtils {
    public static long directorySize(File dir){
        if(dir==null || !dir.exists()) return 0;
        if(dir.isFile()) return dir.length();
        long sum=0;
        for(File f: dir.listFiles()){
            sum += directorySize(f);
        }
        return sum;
    }
}
