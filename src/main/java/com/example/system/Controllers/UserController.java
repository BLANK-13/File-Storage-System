package com.example.system.Controllers;

import com.example.system.ApiUtils.ApiResponse.ApiResponse;
import com.example.system.Models.User;
import com.example.system.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    ////// Only for ADMIN authorization if there's any in the future.
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        return ResponseEntity.ok(new ApiResponse<>(userService.getAll()));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateUser(@AuthenticationPrincipal User user, @RequestBody @Valid User userUpdate) {

        userService.updateUser(user.getId(), userUpdate);
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.userUpdateSuccessMessage()));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> updateUser(@AuthenticationPrincipal User user) throws IOException {

        userService.deleteUser(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.userDeleteSuccessMessage()));

    }

    @GetMapping("/get-my-info")
    public ResponseEntity<ApiResponse<User>> userInfo(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(new ApiResponse<>(userService.userInfo(user.getId())));
    }
}
