package com.crucemelit.model;

import java.util.Date;

import lombok.Data;

public @Data class UserRecordResult {
    private String fullName;
    private String exerciseName;
    private String exerciseResult;
    private String sex;
    private String boxName;
    private Date workoutDate;

    public UserRecordResult(String fullName, String exerciseName, String exerciseResult, String sex, String boxName,
            Date workoutDate) {
        super();
        this.fullName = fullName;
        this.exerciseName = exerciseName;
        this.exerciseResult = exerciseResult;
        this.sex = sex;
        this.boxName = boxName;
        this.workoutDate = workoutDate;
    }

}
