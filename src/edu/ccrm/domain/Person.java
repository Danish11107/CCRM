package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Abstract Person demonstrating Abstraction & Inheritance.
 */
public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected LocalDate createdAt;

    public Person(String id, String fullName, String email) {
        assert id != null && !id.isBlank();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDate.now();
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    public abstract String profile();
}
