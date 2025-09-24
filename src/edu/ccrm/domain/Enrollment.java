package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Enrollment linking student and course and holding marks/grade.
 */
public class Enrollment {
    private final String studentId;
    private final Course course;
    private final LocalDate enrolledOn;
    private Integer marks;
    private Grade grade;

    public Enrollment(String studentId, Course course) {
        this.studentId = studentId;
        this.course = course;
        this.enrolledOn = LocalDate.now();
    }

    public String getStudentId(){ return studentId; }
    public Course getCourse(){ return course; }
    public LocalDate getEnrolledOn(){ return enrolledOn; }
    public void setMarks(int m){ this.marks = m; this.grade = computeGrade(m); }
    public Integer getMarks(){ return marks; }
    public Grade getGrade(){ return grade; }

    private Grade computeGrade(int m){
        if(m>=90) return Grade.S;
        if(m>=80) return Grade.A;
        if(m>=70) return Grade.B;
        if(m>=60) return Grade.C;
        if(m>=50) return Grade.D;
        if(m>=40) return Grade.E;
        return Grade.F;
    }

    @Override
    public String toString(){
        return String.format("Enrollment[student=%s, course=%s, marks=%s, grade=%s]", studentId, course.getCode(), marks, grade);
    }
}
