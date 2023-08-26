package com.java.stcassessment.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/create-file")
    public ResponseEntity<String> createFile(@RequestParam(value = "file_name") String fileName, @RequestParam(value = "folder_id") Integer folderId, @RequestParam(value = "user_email") String userEmail, @RequestParam(value = "file") MultipartFile file) throws IOException {
        logger.info("FileController>>createFile with name: {}, and userEmail: {}", fileName, userEmail);

        byte[] fileContent = file.getBytes();
        fileService.createFile(fileName, folderId, userEmail, fileContent);
        return ResponseEntity.ok("File created successfully");
    }

    @GetMapping("/fileMetadata")
    public String viewFileMetadata(@RequestParam(value = "file_id") Integer fileId) {
        return fileService.getFileMetadataUsingNativeQuery(fileId);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "file_id") Integer fileId) {
        return fileService.downloadFile(fileId);
    }
}
