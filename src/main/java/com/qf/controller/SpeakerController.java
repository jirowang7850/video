package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Speaker;
import com.qf.service.SpeakerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("speaker")
public class SpeakerController {
    @Autowired
    private SpeakerService service;


    @RequestMapping("findSpeakers")
    public Map findSpeakers(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        List<Speaker> list = service.findSpeakers();
        PageInfo<Speaker> pageInfo = new PageInfo<>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());

        return map;
    }

    @RequestMapping("selectSpeaker")
    public List<Speaker> selectSpeaker(){
        return service.selectSpeaker();
    }


    @RequestMapping("deleteById")
    public void deleteById(Integer id){
        service.deleteById(id);
    }

    @RequestMapping("deleteByIds")
    public void deleteById(Integer[] ids){
        service.deleteByIds(ids);
    }

    @RequestMapping("addSpeaker")
    public void addSpeaker(Speaker speaker){
        service.addSpeaker(speaker);
    }

    @RequestMapping("editSpeaker")
    public void editSpeaker(Speaker speaker){
        service.editSpeaker(speaker);
    }
}
