package com.java.stcassessment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.stcassessment.enums.PermissionLevel;
import com.java.stcassessment.exception.ErrorCode;
import com.java.stcassessment.exception.NotFoundException;
import com.java.stcassessment.model.Permission;
import com.java.stcassessment.model.PermissionGroup;
import com.java.stcassessment.repository.PermissionRepository;

@Service
public class PermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionRepository permissionRepository;

    public void savePermissions(PermissionGroup adminGroup) {
        logger.debug("PermissionService>>savePermissions for admin group: {}", adminGroup);

        Permission adminPermission = new Permission("admin@gmail.com", PermissionLevel.ADMIN, adminGroup);
        Permission viewPermission = new Permission("viewer@gmail.com", PermissionLevel.READ, adminGroup);
        Permission editPermission = new Permission("editor@gmail.com", PermissionLevel.WRITE, adminGroup);

        List<Permission> permissionsList = new ArrayList<>();
        permissionsList.add(adminPermission);
        permissionsList.add(viewPermission);
        permissionsList.add(editPermission);
        logger.debug("savePermissions>>permissionsList: {}", permissionsList);

        permissionRepository.saveAll(permissionsList);

    }

    public Permission findByUserEmailAndGroupId(String userEmail, Integer groupId) {
        logger.debug("PermissionService>>findByUserEmailAndGroupId for userEmail: {}, and group id: {}", userEmail, groupId);

        Optional<Permission> optionalPermission = permissionRepository.findByUserEmailAndGroupId(userEmail, groupId);

        if (!optionalPermission.isPresent()) {
            throw new NotFoundException(ErrorCode.NotFoundPermission);
        }
        Permission permission = optionalPermission.get();
        logger.debug("findByUserEmailAndGroupId>>permission: {}", permission);

        return permission;
    }


}
