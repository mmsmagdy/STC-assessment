package com.java.stcassessment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.stcassessment.service.ItemService;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);

    private ItemService itemService;

    @PostMapping
    public ResponseEntity<String> createSpaceWithPermissionGroup() {
        logger.info("SpaceController>>createSpaceWithPermissionGroup..");
        itemService.createSpaceWithPermissionGroup();
        return ResponseEntity.ok("Space created successfully");

    }

}
