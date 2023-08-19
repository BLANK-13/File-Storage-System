package com.example.system.Services;


import com.example.system.ApiUtils.UserExceptions.LoginFailedException;
import com.example.system.ApiUtils.UserExceptions.NoUsersException;
import com.example.system.ApiUtils.UserExceptions.UserNotFoundException;
import com.example.system.Models.User;
import com.example.system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public List<User> getAll() throws NoUsersException {
        List<User> Users = userRepository.findAll();

        if (Users.isEmpty()) {
            throw new NoUsersException();
        }

        return Users;
    }

    public void addUser(User user) {
        user.setPassword(encryptPassword(user.getPassword()));
        user.setUserToken(UUID.randomUUID().toString());

        userRepository.save(user);
    }

    public void updateUser(Integer id, User userUpdate) throws UserNotFoundException {
        User userInDb = userRepository.findUserById(id);

        if (userInDb == null) throw new UserNotFoundException();


        userInDb.setEmail(userUpdate.getEmail());
        userInDb.setName(userUpdate.getName());
        userInDb.setUsername(userUpdate.getUsername());
        userInDb.setPassword(encryptPassword(userUpdate.getPassword()));
        userInDb.setUserToken(UUID.randomUUID().toString());

        userRepository.save(userInDb);
    }


    public void deleteUser(Integer id) {
        User userInDb = userRepository.findUserById(id);

        if (userInDb == null) {
            throw new UserNotFoundException();
        }
        userRepository.delete(userInDb);
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        User userInDb = userRepository.findUserById(id);

        if (userInDb == null) throw new UserNotFoundException();

        return userInDb;
    }


    public User loginUser(String username, String password) throws LoginFailedException {
        User user = userRepository.findByUsernameAndPassword(username, encryptPassword(password));

        if (user == null) throw new LoginFailedException();

        ///// each login we generate a new token just to add more security.
        user.setUserToken(UUID.randomUUID().toString());
        userRepository.save(user);

        return user;
    }


    private String encryptPassword(String password) {

        String encryptedpassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");

            m.update(password.getBytes());

            byte[] bytes = m.digest();

            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); //// this should never be reached anyway.
        }

        return encryptedpassword;
    }


}
