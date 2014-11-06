package com.crucemelit.dto;

import java.util.Date;

import lombok.Data;

import com.crucemelit.model.Record;

public @Data class RecordDto {

    private long id;

    private Result result;

    private UserDto user;

    private WorkoutDto workout;

    private Date date;

    public RecordDto(Record record) {
        if (record != null) {
            this.id = record.getId();
            this.result = record.getResult();
            this.user = new UserDto(record.getUser());
            this.workout = new WorkoutDto(record.getWorkout());
            this.date = record.getDate();
        }
    }

}
