package com.example.system.Controllers;

import com.example.system.ApiUtils.ApiResponse.ApiResponse;
import com.example.system.Models.User;
import com.example.system.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    //////// just for testing this endpoint shows all user tokens which is bad. it shouldn't exist.
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(userService.getAll()));
    }


    //////// just for testing this endpoint shows user token which is bad. it shouldn't exist.
    @GetMapping("/get-id/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(userService.getUserById(id)));
    }


    ///// this is where the user logs in and gets their token to get access to their files anywhere.
    @GetMapping("/log-in/{username}/{password}")
    public ResponseEntity<ApiResponse<String>> loginUser(@PathVariable String username, @PathVariable String password) {
        User user = userService.loginUser(username, password);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Login successful welcome " + user.getName() + " this is your token to access your files copy it: { " + user.getUserToken() + " }"));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addUser(@RequestBody @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(errors.getFieldError().getDefaultMessage()));

        }

        userService.addUser(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ApiResponse.userAddSuccessMessage()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable Integer id, @RequestBody @Valid User userUpdate, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(errors.getFieldError().getDefaultMessage()));

        }


        userService.updateUser(id, userUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ApiResponse.userUpdateSuccessMessage()));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable Integer id) {

        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ApiResponse.userDeleteSuccessMessage()));

    }

}
