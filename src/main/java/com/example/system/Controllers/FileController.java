package com.example.system.Controllers;


import com.example.system.ApiUtils.ApiResponse.ApiResponse;
import com.example.system.Services.FileService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<?>> getMyFilesList(@PathVariable String userToken, @PathVariable Long size){

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesBiggerThan(userToken, size)));
    }

    @GetMapping("/download-by-id/{userToken}/{fileId}")
    public ResponseEntity<ApiResponse<?>> downloadFileById(@PathVariable String userToken, @PathVariable Integer fileId) throws IOException {

        return ResponseEntity.ok(new ApiResponse<>(fileService.downloadFileById(userToken, fileId)));
    }

    @GetMapping("/download-by-name/{userToken}/{fileName}")
    public ResponseEntity<ApiResponse<?>> downloadFileByName(@PathVariable String userToken, @PathVariable String fileName) throws IOException {

        return ResponseEntity.ok(new ApiResponse<>(fileService.downloadFileByName(userToken, fileName)));
    }

    @PostMapping("/upload/{userToken}")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String userToken) throws IOException {

        return ResponseEntity.ok(new ApiResponse<>(fileService.uploadFile(file, userToken)));
    }


}
