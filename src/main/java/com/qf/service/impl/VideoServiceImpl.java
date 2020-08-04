package com.qf.service.impl;

import com.qf.dao.VideoMapper;
import com.qf.pojo.Course;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper mapper;
    @Override
    public List<Video> findVideos() {

        return mapper.findVideos();
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
    public void addVideo(Video video) {
        mapper.insert(video);
    }

    @Override
    public void editVideo(Video video) {
        mapper.updateByPrimaryKeyWithBLOBs(video);
    }

    @Override
    public Video showVideo(Integer videoId) {
        return mapper.selectVideoById(videoId);
    }

    @Override
    public void updatePlayNum(Integer vid, Integer play_num) {
        Map<String, Object> map = new HashMap<>();
        map.put("id",vid);
        map.put("play_num",play_num);
        mapper.updatePlayNum(map);


    }

    @Override
    public List<Video> fuzzyVideo(QueryVo queryVo) {
        return mapper.fuzzyVideo(queryVo);
    }

}
