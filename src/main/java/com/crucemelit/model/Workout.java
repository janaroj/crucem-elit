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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "WORKOUT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Workout extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "person", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String name;
    
    private String comment;

    @OneToMany(mappedBy = "workout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ExerciseGroup> exerciseGroups;

/*    public Integer getResult() {
        if (Utility.isCollectionInitialized(exercises)) {
            for (Exercise exercise : getExercises()) {
                if (exercise.isWod() && exercise.getRecord() != null) {
                    return exercise.getRecord().getResult();
                }
            }
        }
        return null;
    }

    public String getWod() {
        if (Utility.isCollectionInitialized(exercises)) {
            for (Exercise exercise : getExercises()) {
                if (exercise.isWod()) {
                    return exercise.getName();
                }
            }
        }
        return null;
    }
    */
    
}
