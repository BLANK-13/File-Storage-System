package com.example.system.ApiUtils.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private T response;

    public static String userAddSuccessMessage() {
        return "User added successfully";
    }

    public static String userUpdateSuccessMessage() {
        return "User updated successfully";
    }

    public static String userDeleteSuccessMessage() {
        return "User deleted successfully";
    }
}

