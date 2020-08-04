package com.qf.service.impl;

import com.qf.dao.SpeakerMapper;
import com.qf.pojo.Speaker;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerMapper mapper;
    @Override
    public List<Speaker> findSpeakers() {
        return mapper.selectByExampleWithBLOBs(null);
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
    public void addSpeaker(Speaker speaker) {
        mapper.insert(speaker);
    }

    @Override
    public void editSpeaker(Speaker speaker) {
        mapper.updateByExampleWithBLOBs(speaker,null);
    }

    @Override
    public List<Speaker> selectSpeaker() {
        return mapper.selectByExample(null);
    }
}
