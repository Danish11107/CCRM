package edu.ccrm.service;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final DataStore store = DataStore.getInstance();
    @Override public void enroll(String studentId, String courseCode){ store.enrollStudent(studentId, courseCode); }
}
