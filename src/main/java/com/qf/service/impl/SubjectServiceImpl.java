package com.qf.service.impl;


import com.qf.dao.SubjectMapper;
import com.qf.pojo.Subject;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectMapper mapper;
    @Override
    public List<Subject> findSubjects() {
        return mapper.selectByExample(null);
    }

    @Override
    public Subject findSubjectById(Integer subject_id) {
        return mapper.findSubjectById(subject_id);
    }
}
