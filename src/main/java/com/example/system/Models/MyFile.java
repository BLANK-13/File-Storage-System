package com.example.system.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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


    ////// no relations yet I will do it manually.
    @Column(columnDefinition = "int not null")
    private Integer fileOwnerId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String createdAt;
}
