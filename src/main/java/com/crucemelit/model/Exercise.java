package com.crucemelit.model;

import javax.persistence.CascadeType;
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
@Table(name = "EXCERCISE")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "id" })
public @Data class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exerciseModelId")
    private ExerciseModel exerciseModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exerciseGroupId")
    private ExerciseGroup exerciseGroup;

    @OneToOne(mappedBy = "exercise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Record record;

    public Result getResult() {
        if (record != null) {
            return record.getResult();
        }
        return null;
    }

}