package com.example.system.Repository;

import com.example.system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {


    User findUserByUsername(String username);

    User findUserById(Integer id);
}
