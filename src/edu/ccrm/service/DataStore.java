package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import java.io.IOException;

/**
 * Simple in-memory DataStore demonstrating Singleton & basic operations.
 */
public class DataStore {
    private static DataStore instance;
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Course> courses = new LinkedHashMap<>();
    // Example policy: max 24 credits per student
    public static final int MAX_CREDITS = 24;

    private DataStore(){}

    public static synchronized DataStore getInstance(){
        if(instance==null) instance = new DataStore();
        return instance;
    }

    // Student ops
    public void addStudent(Student s){ students.put(s.getId(), s); }
    public Optional<Student> getStudent(String id){ return Optional.ofNullable(students.get(id)); }
    public Collection<Student> listStudents(){ return students.values(); }

    // Course ops
    public void addCourse(Course c){ courses.put(c.getCode(), c); }
    public Optional<Course> getCourse(String code){ return Optional.ofNullable(courses.get(code)); }
    public Collection<Course> listCourses(){ return courses.values(); }

    // Enrollment ops with basic validation
    public void enrollStudent(String studentId, String courseCode){
        Student s = students.get(studentId);
        Course c = courses.get(courseCode);
        if(s==null) throw new IllegalArgumentException("Unknown student: " + studentId);
        if(c==null) throw new IllegalArgumentException("Unknown course: " + courseCode);
        if(s.getEnrollments().stream().anyMatch(e->e.getCourse().getCode().equalsIgnoreCase(courseCode))){
            throw new DuplicateEnrollmentException("Student already enrolled in " + courseCode);
        }
        int newCredits = s.totalEnrolledCredits() + c.getCredits();
        if(newCredits > MAX_CREDITS){
            throw new MaxCreditLimitExceededException("Enrolling in " + courseCode + " would exceed max allowed credits (" + MAX_CREDITS + ")");
        }
        Enrollment e = new Enrollment(s.getId(), c);
        s.enroll(e);
    }

    public void recordMarks(String studentId, String courseCode, int marks){
        Student s = students.get(studentId);
        if(s==null) throw new IllegalArgumentException("Unknown student: " + studentId);
        Optional<Enrollment> oe = s.getEnrollments().stream().filter(en->en.getCourse().getCode().equalsIgnoreCase(courseCode)).findFirst();
        if(oe.isEmpty()) throw new IllegalArgumentException("Student not enrolled in " + courseCode);
        Enrollment e = oe.get();
        e.setMarks(marks);
    }

    // Overloaded convenience method
    public void enrollStudent(Student s, Course c){
        if(s==null || c==null) throw new IllegalArgumentException("Arguments cannot be null");
        enrollStudent(s.getId(), c.getCode());
    }

    // Search example using streams
    public List<Course> searchByInstructor(String instr){
        return courses.values().stream().filter(c->c.getInstructor().equalsIgnoreCase(instr)).collect(Collectors.toList());
    }
}
