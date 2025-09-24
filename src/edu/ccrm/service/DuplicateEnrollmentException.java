package edu.ccrm.service;

public class DuplicateEnrollmentException extends RuntimeException {
    public DuplicateEnrollmentException(String msg){ super(msg); }
}
