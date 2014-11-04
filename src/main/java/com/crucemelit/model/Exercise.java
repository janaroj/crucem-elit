package com.crucemelit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    private String repetitionType;

    @OneToOne(mappedBy = "exercise")
    @JsonBackReference
    private Record record;

    @ManyToOne
    @JoinColumn(name = "workoutId", nullable = false)
    private Workout workout;

}