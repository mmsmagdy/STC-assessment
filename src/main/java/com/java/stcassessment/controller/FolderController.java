package com.java.stcassessment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.stcassessment.service.FolderService;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    @Autowired
    private FolderService folderService;

    @PostMapping
    public ResponseEntity<String> createFolder(@RequestParam(value = "folder_name") String folderName, @RequestParam(value = "space_id") Integer spaceId, @RequestParam(value = "user_email") String userEmail) {
        logger.info("FolderController>>createFolder with name: {}, and userEmail: {}", folderName, userEmail);

        folderService.createFolder(folderName, spaceId, userEmail);

        return ResponseEntity.ok("Folder created successfully");

    }

}
