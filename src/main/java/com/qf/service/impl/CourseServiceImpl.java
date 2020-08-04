package com.qf.service.impl;

import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper mapper;

    @Override
    public List<Course> findCourses() {
        return mapper.findCourses();
    }

    @Override
    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void editCourse(Course course) {
        mapper.updateCourse(course);
    }

    @Override
    public void addCourse(Course course) {
        mapper.insertSelective(course);
    }

    @Override
    public List<Course> selectCourse() {
        return mapper.selectByExample(null);
    }

    @Override
    public Course findCourseById(Integer courseId) {
        return mapper.findCourseById(courseId);
    }


}
