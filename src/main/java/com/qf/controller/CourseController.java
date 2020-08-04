package com.qf.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService service;
    @Autowired
    SubjectService subjectService;

    @ResponseBody
    @RequestMapping("findCourses")
    public Map findCourses(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        List<Course> courseList = service.findCourses();
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    @RequestMapping("selectCourse")
    public List<Course> selectCourse() {
        return service.selectCourse();
    }

    @RequestMapping("deleteById")
    public void deleteById(Integer id) {
        service.deleteById(id);
    }

    @RequestMapping("deleteByIds")
    public void deleteById(Integer[] ids) {
        service.deleteByIds(ids);
    }

    @RequestMapping("editCourse")
    public void editCourse(Course course) {
        service.editCourse(course);
    }
    @RequestMapping("addCourse")
    public void addCourse(Course course) {
        service.addCourse(course);
    }

    @RequestMapping("findById/{subject_id}")
    public ModelAndView findById(@PathVariable Integer subject_id) {
        Subject subject = subjectService.findSubjectById(subject_id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("subject",subject);
        modelAndView.setViewName("course.jsp");
        return modelAndView;
    }

}
