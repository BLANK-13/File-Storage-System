package com.example.system.Services;

import com.example.system.Models.User;
import com.example.system.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersDetailsService implements UserDetailsService {


    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByUsername(username);

        if (user == null) throw new UsernameNotFoundException("Wrong username or password");


        return user;
    }
}