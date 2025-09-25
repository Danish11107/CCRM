package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final DataStore store = DataStore.getInstance();
    @Override public void addStudent(Student s){ store.addStudent(s); }
    @Override public Student findById(String id){ return store.getStudent(id).orElse(null); }
}
