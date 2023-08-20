package com.example.system.ApiUtils.FileExceptions;

import com.example.system.ApiUtils.ApiException;

public class YouHaveNoFilesException extends ApiException {
    public YouHaveNoFilesException() {
        super("No files found, try uploading new files.");
    }
}