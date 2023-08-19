package com.example.system.ApiUtils.UserExceptions;

import com.example.system.ApiUtils.ApiException;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        super("Could not find the user, double check the title please.");
    }
}