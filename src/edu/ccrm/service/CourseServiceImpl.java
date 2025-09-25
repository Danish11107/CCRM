package edu.ccrm.service;

import edu.ccrm.domain.Course;

public class CourseServiceImpl implements CourseService {
    private final DataStore store = DataStore.getInstance();
    @Override public void addCourse(Course c){ store.addCourse(c); }
    @Override public Course findByCode(String code){ return store.getCourse(code).orElse(null); }
}
