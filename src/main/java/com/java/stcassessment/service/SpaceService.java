package com.java.stcassessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.stcassessment.enums.ItemType;
import com.java.stcassessment.model.Item;
import com.java.stcassessment.model.PermissionGroup;

@Service
public class SpaceService {
    private static final Logger logger = LoggerFactory.getLogger(SpaceService.class);

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionGroupService permissionGroupService;
    @Autowired
    private ItemService itemService;

    public Item createSpaceWithPermissionGroup(String spaceName, String adminGroupName) {
        logger.debug("SpaceService>>createSpaceWithPermissionGroup with name: {}, and admin group: {}", spaceName, adminGroupName);

        PermissionGroup adminGroup = permissionGroupService.save(adminGroupName);
        logger.debug("createSpaceWithPermissionGroup>>adminGroup: {}", adminGroup);

        permissionService.savePermissions(adminGroup);

        Item item = new Item(ItemType.SPACE, spaceName, adminGroup);
        logger.debug("createSpaceWithPermissionGroup>>item: {}", item);

        return itemService.save(item);
    }


}
