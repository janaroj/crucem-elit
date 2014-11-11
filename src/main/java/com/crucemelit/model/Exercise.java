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

@Entity
@Table(name = "EXCERCISE")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exerciseTypeId")
    private ExerciseType exerciseType;

    private Boolean countTime;

    private Boolean countWeight;

    private Boolean countRepeats;

    private String comment;

    @OneToOne(mappedBy = "exercise", fetch = FetchType.LAZY)
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exerciseGroupId")
    private ExerciseGroup exerciseGroup;

}