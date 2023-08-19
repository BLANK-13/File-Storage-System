package com.example.system.Repository;

import com.example.system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findUserById(Integer id);

    User findUserByUserToken(String token);

//    User findLibrarianByEmail(String email);

    User findByUsernameAndPassword(String username, String password);

}
