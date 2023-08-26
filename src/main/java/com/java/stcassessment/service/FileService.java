package com.java.stcassessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private ItemService itemService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PermissionService permissionService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createFile(String fileName, Integer folderId, String userEmail, byte[] fileContent) {
        logger.debug("FileService>>createFile with name: {}, and userEmail: {}", fileName, userEmail);

        Item folderItem = itemService.getItem(folderId);
        logger.debug("createFile>>folderItem: {}", folderItem);

        Permission userPermission = permissionService.findByUserEmailAndGroupId(userEmail, folderItem.getPermissionGroup().getId());
        logger.debug("createFile>>userPermission: {}", userPermission);

        if (userPermission == null || userPermission.getPermissionLevel() != PermissionLevel.WRITE) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        Item file = new Item(ItemType.FILE, fileName, folderItem.getPermissionGroup());
        file.setParent(folderItem);
        file = itemService.save(file);
        logger.debug("createFile>>saved item file: {}", file);

        File fileEntity = new File(fileContent, file);
        fileRepository.save(fileEntity);
        logger.debug("createFile>>saved file: {}", fileEntity);

    }

    public ResponseEntity<byte[]> downloadFile(Integer fileId) {
        logger.debug("FileService>>downloadFile with id: {}", fileId);

        File file = fileRepository.findById(fileId).orElse(null);
        logger.debug("downloadFile>>file: {}", file);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getItem().getName());

        return new ResponseEntity<>(file.getBinaryFileData(), headers, HttpStatus.OK);
    }

    public String getFileMetadataUsingNativeQuery(Integer fileId) {
        String sql = "SELECT id, name, type FROM item WHERE id = :fileId";
        try {
            Object[] result = (Object[]) entityManager.createNativeQuery(sql).setParameter("fileId", fileId).getSingleResult();

            Long id = (Long) result[0];
            String name = (String) result[1];
            String type = (String) result[2];

            return "File ID: " + id + "\nFile Name: " + name + "\nFile Type: " + type;
        } catch (Exception e) {
            return null;
        }
    }
}
