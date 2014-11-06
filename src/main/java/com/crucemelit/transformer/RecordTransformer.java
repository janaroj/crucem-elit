package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.RecordDto;
import com.crucemelit.model.Record;

@Component
public class RecordTransformer {

    public RecordDto transformToDto(Record record) {
        return new RecordDto(record);
    }

    public List<RecordDto> transformToDto(List<Record> records) {
        List<RecordDto> list = new ArrayList<>();
        for (Record record : records) {
            list.add(transformToDto(record));
        }
        return list;
    }
}
