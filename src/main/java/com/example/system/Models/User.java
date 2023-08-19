package com.example.system.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_table")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String name;

    @NotBlank(message = "username cannot be empty")
    @Column(columnDefinition = "varchar(255) unique not null")
    private String username;


    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 7, message = "Password has to be at least 7 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\s]{1,}$", message = "Password must contain characters and digits")
    @Column(columnDefinition = "varchar(255) not null")
    private String password;


    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;


    @Column(columnDefinition = "varchar(255) unique not null")
    private String userToken;


}

