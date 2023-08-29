package com.example.system.ApiUtils.UserExceptions;


import com.example.system.ApiUtils.ApiException;

public class LoginFailedException extends ApiException {

    public LoginFailedException() {
        super("Double check the username or password.");
    }
}