package com.java.stcassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.stcassessment.model.PermissionGroup;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Integer> {

    PermissionGroup findByGroupName(String groupName);

}
