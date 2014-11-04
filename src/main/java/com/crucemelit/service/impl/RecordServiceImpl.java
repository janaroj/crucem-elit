package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.Record;
import com.crucemelit.repository.RecordRepository;
import com.crucemelit.service.RecordService;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<Record> getRecords() {
        return recordRepository.findAll();
    }

}
