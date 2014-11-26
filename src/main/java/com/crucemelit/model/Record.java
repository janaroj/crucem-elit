package com.crucemelit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercisegroup_id")
    private ExerciseGroup exerciseGroup;

    private Double time;

    private Double weight;

    private Double repeats;

    private String comment;

    public Result getResult() {
        return new Result(repeats, time, weight, comment);
    }
}