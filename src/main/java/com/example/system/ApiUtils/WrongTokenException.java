package com.example.system.ApiUtils;

public class WrongTokenException extends ApiException {

    public WrongTokenException() {
        super("You provided invalid user token login to get your token.");
    }
}