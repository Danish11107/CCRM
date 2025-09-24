package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

/**
 * Student class with Builder pattern & immutable RegNo inner class.
 */
public class Student extends Person {
    private String regNo;
    private Status status;
    private final Map<String, Enrollment> enrolled = new HashMap<>();
    private LocalDate dob;

    public enum Status { ACTIVE, INACTIVE }

    // Immutable nested static value object for RegNo
    public static final class RegNo {
        private final String value;
        public RegNo(String value) {
            this.value = Objects.requireNonNull(value);
        }
        public String value() { return value; }
        @Override public String toString(){ return value; }
    }

    private Student(Builder b) {
        super(b.id, b.fullName, b.email);
        this.regNo = b.regNo;
        this.status = b.status;
        this.dob = b.dob;
    }

    public static class Builder {
        private final String id;
        private String fullName;
        private String email;
        private String regNo;
        private Status status = Status.ACTIVE;
        private LocalDate dob;

        public Builder(String id) { this.id = id; }
        public Builder name(String n){ this.fullName = n; return this; }
        public Builder email(String e){ this.email = e; return this; }
        public Builder regNo(String r){ this.regNo = r; return this; }
        public Builder status(Status s){ this.status = s; return this; }
        public Builder dob(LocalDate d){ this.dob = d; return this; }
        public Student build(){ return new Student(this); }
    }

    public void enroll(Enrollment e) { enrolled.put(e.getCourse().getCode(), e); }
    public void unenroll(String courseCode){ enrolled.remove(courseCode); }
    public Collection<Enrollment> getEnrollments(){ return enrolled.values(); }
    public String getRegNo(){ return regNo; }
    public Status getStatus(){ return status; }
    public void setStatus(Status s){ this.status = s; }

    /**
     * Calculate GPA as weighted average of grade points by course credits.
     * Returns Optional.empty() if no graded enrollments exist.
     */
    public OptionalDouble calculateGPA(){
        double totalPoints = 0.0;
        int totalCredits = 0;
        for(Enrollment e : enrolled.values()){
            Grade g = e.getGrade();
            if(g!=null && e.getCourse()!=null){
                int credits = e.getCourse().getCredits();
                totalPoints += g.points() * credits;
                totalCredits += credits;
            }
        }
        if(totalCredits==0) return OptionalDouble.empty();
        return OptionalDouble.of(totalPoints / totalCredits);
    }

    public int totalEnrolledCredits(){
        return enrolled.values().stream().mapToInt(e->e.getCourse().getCredits()).sum();
    }

    @Override
    public String profile() {
        return String.format("Student[id=%s, regNo=%s, name=%s, email=%s, status=%s, credits=%d]", id, regNo, fullName, email, status, totalEnrolledCredits());
    }

    @Override
    public String toString(){ return profile(); }
}
