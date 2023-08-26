package com.java.stcassessment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.stcassessment.service.SpaceService;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);

    @Autowired
    private SpaceService spaceService;

    @PostMapping
    public ResponseEntity<String> createSpaceWithPermissionGroup(@RequestParam(value = "space_name") String spaceName, @RequestParam(value = "admin_group_name") String adminGroupName) {
        logger.info("SpaceController>>createSpaceWithPermissionGroup with name: {}, and admin group name: {}", spaceName, adminGroupName);
        spaceService.createSpaceWithPermissionGroup(spaceName, adminGroupName);
        return ResponseEntity.ok("Space created successfully");

    }

}
