package com.java.stcassessment.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.stcassessment.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

    private FileService fileService;


    @PostMapping("/create-file")
    public ResponseEntity<String> createFile(@RequestParam String fileName, @RequestParam String folderName, @RequestParam String userEmail, @RequestParam MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        fileService.createFile(fileName, folderName, userEmail, fileContent);
        return ResponseEntity.ok("File created successfully");
    }

    @GetMapping("/fileMetadata")
    public String viewFileMetadata(@RequestParam Long fileId) {
        return fileService.getFileMetadataUsingNativeQuery(fileId);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam Integer fileId) {
        return fileService.downloadFile(fileId);
    }
}
