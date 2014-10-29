package com.crucemelit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.crucemelit.domain.Sex;
import com.crucemelit.util.Utility;
import com.crucemelit.web.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PERSON")
@NoArgsConstructor
// For debugging, remove later
@ToString(exclude = { "gym", "friendOf", "friends", "picture", "token", "passwordHash" })
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

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "gym")
    private Gym gym;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "connections", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
    @JsonIgnore
    private List<User> friends;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "connections", joinColumns = @JoinColumn(name = "friendId"), inverseJoinColumns = @JoinColumn(name = "personId"))
    @JsonIgnore
    private List<User> friendOf;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Workout> workouts;

    @JsonIgnore
    private byte[] picture;

    @Column(name = "INVALID_LOGIN_COUNT")
    @JsonIgnore
    private int invalidLoginCount;

    @Transient
    private String token;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    @Override
    public String getName() {
        if (getFirstName() == null && getLastName() == null) {
            return getEmail();
        }
        return Utility.formatStrings(getFirstName(), getLastName());
    }

    @Override
    @JsonIgnore
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
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public List<User> getContactsFromGym() {
        if (gym != null) {
            return gym.getUsers();
        }
        return (List<User>) Utility.EMPTY_LIST;
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.role);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);
        return authorities;
    }

    @JsonIgnore
    public void resetInvalidLoginCount() {
        this.invalidLoginCount = 0;
    }

    @JsonIgnore
    public void increaseInvalidLoginCount() {
        this.invalidLoginCount++;
    }

}
