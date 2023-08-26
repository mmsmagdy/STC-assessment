package com.java.stcassessment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.stcassessment.enums.ItemType;
import com.java.stcassessment.enums.PermissionLevel;
import com.java.stcassessment.exception.AccessDeniedException;
import com.java.stcassessment.exception.ErrorCode;
import com.java.stcassessment.exception.NotFoundException;
import com.java.stcassessment.model.Item;
import com.java.stcassessment.model.Permission;
import com.java.stcassessment.model.PermissionGroup;
import com.java.stcassessment.repository.ItemRepository;
import com.java.stcassessment.repository.PermissionGroupRepository;
import com.java.stcassessment.repository.PermissionRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public Item createSpaceWithPermissionGroup() {
        PermissionGroup adminGroup = new PermissionGroup("admin");
        adminGroup = permissionGroupRepository.save(adminGroup);

        Permission adminPermission = new Permission("admin@gmail.com", PermissionLevel.ADMIN, adminGroup);
        Permission viewPermission = new Permission("viewer@gmail.com", PermissionLevel.READ, adminGroup);
        Permission editPermission = new Permission("editor@gmail.com", PermissionLevel.WRITE, adminGroup);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(adminPermission);
        permissions.add(viewPermission);
        permissions.add(editPermission);
        permissionRepository.saveAll(permissions);

        Item item = new Item(ItemType.SPACE, "stc-assessments", adminGroup);
        return itemRepository.save(item);
    }

    public Item createFolder(String folderName, String spaceName, String userEmail) {

        Item spaceItem = getItem(spaceName);

        Permission userPermission = permissionRepository.findByUserEmailAndItemId(userEmail, spaceItem.getId());
        if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.WRITE) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        Item folder = new Item(ItemType.FOLDER, folderName, userPermission.getGroup());
        folder.setParent(spaceItem);
        folder = itemRepository.save(folder);
        return folder;
    }

    public Item getItem(String Name) {
        Optional<Item> optionalItem = itemRepository.findByName(Name);

        if (!optionalItem.isPresent()) {
            throw new NotFoundException(ErrorCode.NotFoundItem);
        }
        Item item = optionalItem.get();

        return item;
    }

    public Item save(Item file) {
        file = itemRepository.save(file);
        return file;
    }


}
