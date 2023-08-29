package com.example.system.Services;

import com.example.system.ApiUtils.UserExceptions.UsernameAlreadyUsedException;
import com.example.system.Models.User;
import com.example.system.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthRepository authRepository;


    public void register(User user) {
        User usernameCheck = authRepository.findUserByUsername(user.getUsername());

        /// if it's not null that means there's already a user with the new username.
        if (usernameCheck != null) {
            throw new UsernameAlreadyUsedException();
        }
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("USER");

        authRepository.save(user);

    }

//    public void login(User user) {
//        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(hash);
//        user.setRole("USER");
//
//        authRepository.save(user);
//
//    }

}