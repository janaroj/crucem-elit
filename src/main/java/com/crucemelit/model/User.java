package com.crucemelit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.crucemelit.domain.Gender;
import com.crucemelit.domain.Role;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.util.Utility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PERSON")
@NoArgsConstructor
@ToString(of = { "id" })
@EqualsAndHashCode(callSuper = false)
public @Data class User extends BaseEntity implements UserDetails, Suggestable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Email
    private String email;

    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    private String firstName;

    private String lastName;

    private Double weight;

    private Integer length;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym")
    private Gym gym;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "connections", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
    private List<User> friends;

    @ManyToMany(mappedBy = "friends", fetch = FetchType.LAZY)
    private List<User> friendOf;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts;

    private byte[] picture;

    @Column(name = "INVALID_LOGIN_COUNT")
    private int invalidLoginCount;

    @Transient
    private String token;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String getName() {
        if (getFirstName() == null && getLastName() == null) {
            return getEmail();
        }
        return Utility.formatStrings(getFirstName(), getLastName());
    }

    @Override
    public boolean isAccountNonLocked() {
        return getInvalidLoginCount() <= 5;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getPasswordHash();
    }

    @JsonIgnore
    public String getPasswordHash() {
        return this.passwordHash;
    }

    @JsonProperty
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<User> getContactsFromGym() {
        if (gym != null) {
            return gym.getUsers();
        }
        return (List<User>) Utility.EMPTY_LIST;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(this.role.name());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);
        return authorities;
    }

    public void changeRole() {
        this.role = role == Role.USER ? Role.ADMIN : Role.USER;
    }

    public void resetInvalidLoginCount() {
        this.invalidLoginCount = 0;
    }

    public void increaseInvalidLoginCount() {
        this.invalidLoginCount++;
    }

    public void addFriend(User friend) {
        this.getFriends().add(friend);
        friend.getFriendOf().add(this);
    }

    public void removeFriend(User friend) {
        this.getFriends().remove(friend);
        friend.getFriendOf().remove(this);
    }

    public void addWorkout(Workout workout) {
        this.getWorkouts().add(workout);
        workout.setUser(this);
    }

    public void removeWorkout(Workout workout) {
        this.getWorkouts().remove(workout);
    }

    public Workout getWorkout(long id) {
        for (Workout workout : getWorkouts()) {
            if (workout.getId() == id) {
                return workout;
            }
        }

        throw new EntityNotFoundException();
    }
}
