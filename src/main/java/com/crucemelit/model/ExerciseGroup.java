package com.crucemelit.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EXCERCISEGROUP")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data
class ExerciseGroup extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	private String name;

    @ManyToOne
    @JoinColumn(name = "workoutId")
	private Workout workout;

	@OneToMany(mappedBy = "exerciseGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Exercise> exercises;
	
    private boolean wod;
    
    @OneToOne(mappedBy = "exerciseGroup")
    private Record record;

}
