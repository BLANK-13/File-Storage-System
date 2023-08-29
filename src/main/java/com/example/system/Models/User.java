package com.example.system.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message = "Username cannot be empty")
    @Column(columnDefinition = "varchar(255) unique not null")
    private String username;

    //// no validation because we can only accept USER,
    // and it'll be automatically set there's no a need for ADMIN because files are private.
    //if there's we'll do it manually.
    @Column(columnDefinition = "varchar(255) not null")
    private String role;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 7, message = "Password has to be at least 7 characters")
    @Column(columnDefinition = "varchar(255) not null")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\s]{1,}$", message = "Password must contain characters and digits")
    private String password;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<MyFile> files;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
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
}