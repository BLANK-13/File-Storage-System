package com.example.system.ApiUtils;

public class ApiException extends RuntimeException{

    protected ApiException(String errMessage){
        super(errMessage);
    }
}