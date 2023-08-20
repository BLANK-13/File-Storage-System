package com.example.system.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files_table")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "int not null")
    private Long size;


    //// file name is not unique for all users because two users could have the same file name e.g. ID.pdf
    @Column(columnDefinition = "varchar(255) not null")
    private String fileName;

    //// file name is not unique for all users because two users could have the same file name e.g. ID.pdf
    @Column(columnDefinition = "varchar(255) not null")
    private String fileType;


    ////// no relations yet I will do it manually.
    @Column(columnDefinition = "int not null")
    private Integer fileOwnerId;


    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
