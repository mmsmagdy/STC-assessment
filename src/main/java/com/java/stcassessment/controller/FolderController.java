package com.java.stcassessment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.stcassessment.service.ItemService;

@RestController
@RequestMapping("/folder")
public class FolderController {


    private ItemService itemService;

    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestParam String folderName, @RequestParam String spaceName, @RequestParam String userEmail) {
        itemService.createFolder(folderName, spaceName, userEmail);

        return ResponseEntity.ok("Folder created successfully");

    }

}
