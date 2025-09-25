package edu.ccrm.util;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import java.util.Comparator;

/**
 * Common comparators using lambdas.
 */
public class Comparators {
    public static final Comparator<Student> BY_NAME = Comparator.comparing(Student::getFullName);
    public static final Comparator<Student> BY_CREDITS = Comparator.comparingInt(s -> {
        try { return s.totalEnrolledCredits(); } catch(Exception e) { return 0; }
    });
    public static final Comparator<Course> BY_TITLE = Comparator.comparing(Course::getTitle);
}
