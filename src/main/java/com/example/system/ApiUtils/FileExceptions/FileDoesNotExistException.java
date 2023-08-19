package com.example.system.ApiUtils.FileExceptions;

import com.example.system.ApiUtils.ApiException;

public class FileDoesNotExistException extends ApiException {
    public FileDoesNotExistException() {
        super("There was a problem in the file you provided");
    }
}