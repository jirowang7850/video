package com.qf.service;

import com.qf.pojo.Course;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;

import java.util.List;

public interface VideoService {
    List<Video> findVideos();

    void deleteById(Integer id);

    void deleteByIds(Integer[] ids);

    void addVideo(Video video);

    void editVideo(Video video);

    Video showVideo(Integer videoId);

    void updatePlayNum(Integer vid,Integer play_num);

    List<Video> fuzzyVideo(QueryVo queryVo);
}
