package com.crucemelit.model;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXERCISEMODEL")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class ExerciseModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exerciseTypeId")
    private ExerciseType exerciseType;

    private boolean countTime;

    private boolean countWeight;

    private boolean countRepeats;

    private String comment;

    @OneToMany(mappedBy = "exerciseModel", fetch = FetchType.LAZY)
    private List<Exercise> exercises;

}
