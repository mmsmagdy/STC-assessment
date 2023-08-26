package com.java.stcassessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.stcassessment.enums.ItemType;
import com.java.stcassessment.enums.PermissionLevel;
import com.java.stcassessment.exception.AccessDeniedException;
import com.java.stcassessment.exception.ErrorCode;
import com.java.stcassessment.model.Item;
import com.java.stcassessment.model.Permission;

@Service
public class FolderService {
    private static final Logger logger = LoggerFactory.getLogger(FolderService.class);

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ItemService itemService;

    public Item createFolder(String folderName, Integer spaceId, String userEmail) {
        logger.debug("FolderService>>createFolder with name: {}, and userEmail: {}", folderName, userEmail);

        Item spaceItem = itemService.getItem(spaceId);
        logger.debug("createFolder>>spaceItem: {}", spaceItem);

        Permission userPermission = permissionService.findByUserEmailAndGroupId(userEmail, spaceItem.getPermissionGroup().getId());        
        logger.debug("createFolder>>userPermission: {}", userPermission);

        if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.WRITE) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        Item folder = new Item(ItemType.FOLDER, folderName, spaceItem.getPermissionGroup());
        folder.setParent(spaceItem);
        folder = itemService.save(folder);
        logger.debug("createFolder>>saved folder: {}", folder);

        return folder;
    }

}
