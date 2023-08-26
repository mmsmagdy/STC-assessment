package com.java.stcassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.stcassessment.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Permission findByUserEmailAndItemId(String userEmail, Integer id);

}
