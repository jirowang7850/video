package com.qf.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.qf.pojo.Course;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;
import com.qf.service.CourseService;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoController {
    @Autowired
    VideoService service;

    @Autowired
    private CourseService courseService;

    @RequestMapping("findVideos")
    public Map findVideos(@RequestParam(required = false) Integer page,@RequestParam(required = false)Integer limit){
        PageHelper.startPage(page,limit);
        List<Video> videoList = service.findVideos();
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);

        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());

        return map;
    }

    @RequestMapping("addVideo")
    public void addVideo(Video video){
        video.setPlayNum(123);
        service.addVideo(video);

    }

    @RequestMapping("deleteById")
    public void deleteById(Integer id){
        service.deleteById(id);

    }

    @RequestMapping("deleteByIds")
    public void deleteById(Integer[] ids){
        service.deleteByIds(ids);

    }

    @RequestMapping("editVideo")
    public void editVideo(Video video){
        video.setPlayNum(134);
        service.editVideo(video);

    }


    @RequestMapping("showVideo")
    public ModelAndView showVideo(Integer videoId,String subjectName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("subjectName",subjectName);

        Video video = service.showVideo(videoId);
        Course course = courseService.findCourseById(video.getCourseId());
        modelAndView.addObject("course",course);
        modelAndView.addObject("video",video);
        modelAndView.setViewName("section.jsp");
        return modelAndView;
    }

    @RequestMapping("updatePlayNum")
    public void updatePlayNum(Video video) {
        video.setPlayNum(video.getPlayNum()+1);
        service.updatePlayNum(video.getId(),video.getPlayNum());
    }


    @RequestMapping("fuzzyVideo")
    public Map fuzzyVideo(QueryVo queryVo, Model model) {

        Map<String, Object> map = new HashMap<>();

        PageHelper.startPage(queryVo.getPage(),queryVo.getSize());
        List<Video> videoList=service.fuzzyVideo(queryVo);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }
}
