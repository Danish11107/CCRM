package edu.ccrm.service;

import edu.ccrm.domain.Student;

public interface StudentService {
    void addStudent(Student s);
    Student findById(String id);
}
