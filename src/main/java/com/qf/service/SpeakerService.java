package com.qf.service;

import com.qf.pojo.Speaker;

import java.util.List;

public interface SpeakerService {
    List<Speaker> findSpeakers();

    void deleteById(Integer id);

    void deleteByIds(Integer[] ids);

    void addSpeaker(Speaker speaker);

    void editSpeaker(Speaker speaker);

    List<Speaker> selectSpeaker();
}
