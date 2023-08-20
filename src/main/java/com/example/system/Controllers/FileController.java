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
    private final int MAX_FILE_SIZE = 100;


    @GetMapping("/get-my-files/{userToken}")
    public ResponseEntity<ApiResponse<?>> getMyFilesList(@PathVariable String userToken) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFiles(userToken)));
    }

    @GetMapping("/get-files-by-type/{userToken}/{mediaType}")
    public ResponseEntity<ApiResponse<?>> getMyFilesByType(@PathVariable String userToken, @PathVariable String mediaType) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesByType(userToken, mediaType)));
    }

    @GetMapping("/get-my-files-above/{userToken}/{size}")
    public ResponseEntity<ApiResponse<?>> getMyFilesListBiggerThan(@PathVariable String userToken, @PathVariable Long size) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesBiggerThan(userToken, size)));
    }

    @GetMapping("/get-my-files-less/{userToken}/{size}")
    public ResponseEntity<ApiResponse<?>> getMyFilesListSmallerThan(@PathVariable String userToken, @PathVariable Long size) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesLessThan(userToken, size)));
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

    @GetMapping("/get-allowed")
    public ResponseEntity<ApiResponse<?>> getMaximumUploadSize() {

        return ResponseEntity.ok(new ApiResponse<>("You can upload up to: " + MAX_FILE_SIZE + "MB"));
    }
}
