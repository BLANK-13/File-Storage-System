package com.example.system.Controllers;


import com.example.system.ApiUtils.ApiResponse.ApiResponse;
import com.example.system.Services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;


    @GetMapping("/get-my-files/{userToken}")
    public ResponseEntity<ApiResponse<?>> getMyFilesList(@PathVariable String userToken) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFiles(userToken)));
    }

    @GetMapping("/get-my-files-above/{userToken}/{size}")
    public ResponseEntity<ApiResponse<?>> getMyFilesList(@PathVariable String userToken, @PathVariable Long size) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesBiggerThan(userToken, size)));
    }

    @GetMapping("/download-by-id/{userToken}/{fileId}")
    public ResponseEntity<?> downloadFileById(@PathVariable String userToken, @PathVariable Integer fileId) throws IOException {

        var file = fileService.downloadFileById(userToken, fileId);
        return ResponseEntity.status(HttpStatus.OK).contentType(file.mediaType()).body(file.data());
    }

    @GetMapping("/download-by-name/{userToken}/{fileName}")
    public ResponseEntity<?> downloadFileByName(@PathVariable String userToken, @PathVariable String fileName) throws IOException {

        var file = fileService.downloadFileByName(userToken, fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(file.mediaType()).body(file.data());
    }

    @PostMapping("/upload/{userToken}")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String userToken) throws IOException {

        return ResponseEntity.ok(new ApiResponse<>(fileService.uploadFile(file, userToken)));
    }


}
