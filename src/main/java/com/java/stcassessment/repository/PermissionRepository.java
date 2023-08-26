package com.java.stcassessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.stcassessment.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByUserEmailAndGroupId(String userEmail, Integer groupId);

}
