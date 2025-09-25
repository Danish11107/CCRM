package edu.ccrm.service;

import edu.ccrm.domain.Course;

public interface CourseService {
    void addCourse(Course c);
    Course findByCode(String code);
}
