package com.example.system.ApiUtils.UserExceptions;

import com.example.system.ApiUtils.ApiException;

public class NoUsersException extends ApiException {
    public NoUsersException() {
        super("No users in the database at the moment");
    }
}
