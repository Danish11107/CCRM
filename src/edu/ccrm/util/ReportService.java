package edu.ccrm.util;

import edu.ccrm.service.DataStore;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Grade;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * ReportService produces aggregate reports such as GPA distribution.
 */
public class ReportService {
    public static Map<String, Long> gpaDistribution(){
        return DataStore.getInstance().listStudents().stream()
            .map(s -> s.calculateGPA().isPresent() ? String.format("%.2f", s.calculateGPA().getAsDouble()) : "N/A")
            .collect(Collectors.groupingBy(g->g, Collectors.counting()));
    }
}
