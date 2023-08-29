package com.example.system.ApiUtils.UserExceptions;

import com.example.system.ApiUtils.ApiException;

public class UsernameAlreadyUsedException extends ApiException {
    public UsernameAlreadyUsedException() {

        super("This username is already taken.");
    }
}