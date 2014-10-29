package com.crucemelit.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.crucemelit.domain.Sex;

@AllArgsConstructor
public @Data class UserRecordResult {
    private String name;
    private String exerciseName;
    private String exerciseResult;
    private Sex sex;
    private String boxName;
    private Date workoutDate;

}
