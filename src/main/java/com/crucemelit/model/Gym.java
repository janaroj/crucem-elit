package com.crucemelit.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.crucemelit.util.Utility;

@Entity
@Table(name = "GYM")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Gym extends BaseEntity implements Suggestable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String address;

    private String city;

    private String trainers;

    private String contact;

    @OneToMany(mappedBy = "gym", fetch = FetchType.LAZY)
    private List<User> users;

    private byte[] picture;

    public String getFullAddress() {
    	 return Utility.formatStrings(getCity(), getAddress());
    }

}
