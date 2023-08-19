package com.example.system.ApiUtils.FileExceptions;

import com.example.system.ApiUtils.ApiException;

public class YouHaveNoFilesException extends ApiException {
    public YouHaveNoFilesException() {
        super("You have not uploaded files yet, try uploading a file.");
    }
}