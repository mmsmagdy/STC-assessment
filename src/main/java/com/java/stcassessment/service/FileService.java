package com.java.stcassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.stcassessment.enums.ItemType;
import com.java.stcassessment.enums.PermissionLevel;
import com.java.stcassessment.exception.AccessDeniedException;
import com.java.stcassessment.exception.ErrorCode;
import com.java.stcassessment.model.File;
import com.java.stcassessment.model.Item;
import com.java.stcassessment.model.Permission;
import com.java.stcassessment.repository.FileRepository;
import com.java.stcassessment.repository.PermissionRepository;

@Service
public class FileService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public void createFile(String fileName, String folderName, String userEmail, byte[] fileContent) {
        Item folderItem = itemService.getItem(folderName);

        Permission userPermission = permissionRepository.findByUserEmailAndItemId(userEmail, folderItem.getId());

        if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.WRITE) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        Item file = new Item(ItemType.FILE, fileName, userPermission.getGroup());
        file.setParent(folderItem);
        file = itemService.save(file);

        File fileEntity = new File(fileContent, file);
        fileRepository.save(fileEntity);
    }

    public ResponseEntity<byte[]> downloadFile(Integer fileId) {
        File file = fileRepository.findById(fileId).orElse(null);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getItem().getName());

        return new ResponseEntity<>(file.getBinaryData(), headers, HttpStatus.OK);
    }

    public String getFileMetadataUsingNativeQuery(Long fileId) {
        // TODO Auto-generated method stub
        return null;
    }
}
