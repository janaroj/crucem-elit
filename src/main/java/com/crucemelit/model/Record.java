package com.crucemelit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.crucemelit.dto.Result;

@Entity
@Table(name = "WORKOUTEXERCISERESULT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Exercise exercise;

    @OneToOne
    @PrimaryKeyJoinColumn
    private ExerciseGroup exerciseGroup;

    private Double time;

    private Double weight;

    private Double repeats;

    private String comment;

    public Result getResult() {
        return new Result(repeats, time, weight, comment);
    }

    public User getUser() {
        return getExercise().getExerciseGroup().getWorkout().getUser();
    }

    public Workout getWorkout() {
        return getExercise().getExerciseGroup().getWorkout();
    }

    public Date getDate() {
        return getExercise().getExerciseGroup().getWorkout().getDate();
    }

}