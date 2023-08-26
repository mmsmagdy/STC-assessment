package com.java.stcassessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.stcassessment.model.PermissionGroup;
import com.java.stcassessment.repository.PermissionGroupRepository;

@Service
public class PermissionGroupService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionGroupService.class);

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    public PermissionGroup save(String adminGroupName) {
        logger.debug("PermissionGroupService>>save with name: {}", adminGroupName);

        PermissionGroup adminGroup = new PermissionGroup(adminGroupName);
        adminGroup = permissionGroupRepository.save(adminGroup);
        return adminGroup;
    }


}
