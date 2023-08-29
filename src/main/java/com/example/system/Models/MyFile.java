package com.example.system.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files_table")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "int not null")
    private Integer size;


    //// file name is not unique for all users because two users could have the same file name e.g. ID.pdf
    @Column(columnDefinition = "varchar(255) not null")
    private String fileName;

    @Column(columnDefinition = "varchar(255) not null")
    private String fileType;


    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

}
