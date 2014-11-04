package com.crucemelit.model;

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

@Entity
@Table(name = "WORKOUTEXERCISERESULT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Result extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Exercise exercise;

    @Column(name = "result")
    private int result;
    

}