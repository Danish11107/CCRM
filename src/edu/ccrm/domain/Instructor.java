package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Instructor extends Person to demonstrate inheritance and polymorphism.
 */
public class Instructor extends Person {
    private String employeeId;
    private String department;

    public Instructor(String id, String fullName, String email, String employeeId, String department) {
        super(id, fullName, email);
        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }

    @Override
    public String profile() {
        return String.format("Instructor[id=%s, emp=%s, name=%s, dept=%s]", getId(), employeeId, getFullName(), department);
    }

    @Override
    public String toString(){ return profile(); }
}
