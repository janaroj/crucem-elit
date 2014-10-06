package com.crucemelit.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PERSON")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Email
    private String email;

    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    private String firstName;

    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gym")
    private Gym gym;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "connections", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
    private List<User> friends;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "connections", joinColumns = @JoinColumn(name = "friendId"), inverseJoinColumns = @JoinColumn(name = "personId"))
    private List<User> friendOf;

    @Column(name = "INVALID_LOGIN_COUNT")
    private int invalidLoginCount;

    @Transient
    private Collection<GrantedAuthority> authorities;

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
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

    public void addFriend(User user) {
        friends.add(user);

    }

}
