package com.example.system.Controllers;


import com.example.system.ApiUtils.ApiResponse.ApiResponse;
import com.example.system.Models.User;
import com.example.system.Services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;


    @GetMapping("/get-my-files")
    public ResponseEntity<ApiResponse<?>> getMyFileList(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFiles(user.getId())));
    }

    @GetMapping("/get-files-by-type/{mediaType}")
    public ResponseEntity<ApiResponse<?>> getMyFilesByType(@AuthenticationPrincipal User user, @PathVariable String mediaType) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesByType(user.getId(), mediaType)));
    }

    @GetMapping("/get-my-files-above/{size}")
    public ResponseEntity<ApiResponse<?>> getMyFileListBiggerThan(@AuthenticationPrincipal User user, @PathVariable Integer size) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesBiggerThan(user.getId(), size)));
    }

    @GetMapping("/get-my-files-less/{size}")
    public ResponseEntity<ApiResponse<?>> getMyFileListSmallerThan(@AuthenticationPrincipal User user, @PathVariable Integer size) {

        return ResponseEntity.ok(new ApiResponse<>(fileService.getMyFilesLessThan(user.getId(), size)));
    }

    @GetMapping("/download-by-id/{fileId}")
    public ResponseEntity<?> downloadFileById(@AuthenticationPrincipal User user, @PathVariable Integer fileId) throws IOException {

        var file = fileService.downloadFileById(user.getId(), fileId);
        return ResponseEntity.status(HttpStatus.OK).contentType(file.mediaType()).body(file.data());
    }

    @GetMapping("/download-by-name/{fileName}")
    public ResponseEntity<?> downloadFileByName(@AuthenticationPrincipal User user, @PathVariable String fileName) throws IOException {

        var file = fileService.downloadFileByName(user.getId(), fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(file.mediaType()).body(file.data());
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {

        return ResponseEntity.ok(new ApiResponse<>(fileService.uploadFile(file, user.getId())));
    }

    @GetMapping("/get-allowed")
    public ResponseEntity<ApiResponse<?>> getMaximumUploadSize() {

        final int MAX_FILE_SIZE = 100;
        return ResponseEntity.ok(new ApiResponse<>("You can upload up to: " + MAX_FILE_SIZE + "MB"));
    }
}
