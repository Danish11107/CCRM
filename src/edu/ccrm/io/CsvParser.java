package edu.ccrm.io;

import java.util.Arrays;
import java.util.List;

/**
 * Simple CSV parser utility.
 */
public class CsvParser {
    public static List<String> parseLine(String line){
        return Arrays.asList(line.split(",")); 
    }
}
