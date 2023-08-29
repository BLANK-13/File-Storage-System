package com.example.system.Services;


import com.example.system.ApiUtils.UserExceptions.NoUsersException;
import com.example.system.ApiUtils.UserExceptions.UserNotFoundException;
import com.example.system.ApiUtils.UserExceptions.UsernameAlreadyUsedException;
import com.example.system.Models.User;
import com.example.system.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final AuthRepository authRepository;
    private final FileService fileService;


    public List<User> getAll() throws NoUsersException {
        List<User> Users = authRepository.findAll();

        if (Users.isEmpty()) {
            throw new NoUsersException();
        }

        return Users;
    }


    public void updateUser(Integer userId, User userUpdate) throws UsernameAlreadyUsedException {
        User userInDb = authRepository.findUserById(userId);
        User usernameCheck = authRepository.findUserByUsername(userUpdate.getUsername());

        /// if it's not null that means there's already a user with the new username.
        if (usernameCheck != null) {
            throw new UsernameAlreadyUsedException();
        }

        String hash = new BCryptPasswordEncoder().encode(userUpdate.getPassword());

        userInDb.setUsername(userUpdate.getUsername());
        userInDb.setPassword(hash);

        authRepository.save(userInDb);
    }


    public void deleteUser(Integer id) throws IOException {
        User userInDb = authRepository.findUserById(id);

        if (userInDb == null) {
            throw new UserNotFoundException();
        }


//// We won't actually delete a user from the db since you can't delete someone who's in I need to test it more.
// I tried utilizing the logout in the filter chain, but it didn't work.
// For now, I'll delete all user files from the server and keep the user in the db.


//        authRepository.delete(userInDb);


        ///// maybe this user have no files in our server so we check for that just before deleting their directory
        if (!userInDb.getFiles().isEmpty()) {
            fileService.deleteUserFiles(userInDb.getId());
        }
    }


    public User userInfo(Integer userId) {

        return authRepository.findUserById(userId);
    }


//    private String encryptPassword(String password) {
//
//        String encryptedpassword = null;
//        try {
//            MessageDigest m = MessageDigest.getInstance("MD5");
//
//            m.update(password.getBytes());
//
//            byte[] bytes = m.digest();
//
//            StringBuilder s = new StringBuilder();
//            for (byte aByte : bytes) {
//                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
//            }
//
//            encryptedpassword = s.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace(); //// this should never be reached anyway.
//        }
//
//        return encryptedpassword;
//    }


}
