package com.qf.service;

import com.qf.pojo.Course;
import com.qf.pojo.Subject;

import java.util.List;

public interface CourseService {
    List<Course> findCourses();

    void deleteById(Integer id);

    void deleteByIds(Integer[] ids);

    void editCourse(Course course);

    void addCourse(Course course);

    List<Course> selectCourse();

    Course findCourseById(Integer courseId);
}
