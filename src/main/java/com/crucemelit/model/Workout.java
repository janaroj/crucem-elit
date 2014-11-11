package com.crucemelit.model;

import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "workout", fetch = FetchType.LAZY)
    private List<ExerciseGroup> exerciseGroups;

    public Result getResult() {
        for (ExerciseGroup exerciseGroup : getExerciseGroups()) {
            if (exerciseGroup.isWod() && exerciseGroup.getRecord() != null) {
                return exerciseGroup.getRecord().getResult();
            }
        }
        return null;
    }

    public String getWod() {
        for (ExerciseGroup exerciseGroups : getExerciseGroups()) {
            if (exerciseGroups.isWod()) {
                return exerciseGroups.getName();
            }
        }
        return null;
    }

}
