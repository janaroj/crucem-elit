package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.Result;
import com.crucemelit.repository.ResultRepository;
import com.crucemelit.service.ResultService;

@Service
@Transactional
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public List<Result> getResults() {
        return resultRepository.findAll();
    }

}
