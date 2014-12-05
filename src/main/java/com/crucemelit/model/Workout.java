package com.crucemelit.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.crucemelit.dto.Result;

@Entity
@Table(name = "WORKOUT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Workout extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String name;

    private String comment;

    private String gymName;

    @Column(nullable = false)
    private Boolean completed;

    @OneToMany(mappedBy = "workout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExerciseGroup> exerciseGroups;

    public void setExerciseGroups(List<ExerciseGroup> groups) {
        for (ExerciseGroup group : groups) {
            group.setWorkout(this);
        }
        this.exerciseGroups = groups;
    }

    public Result getResult() {
        Record record = getWod().getRecord();
        if (record != null) {
            return record.getResult();
        }
        return null;
    }

    public ExerciseGroup getWod() {
        for (ExerciseGroup exerciseGroup : getExerciseGroups()) {
            if (exerciseGroup.isWod()) {
                return exerciseGroup;
            }
        }
        return null;
    }

}
