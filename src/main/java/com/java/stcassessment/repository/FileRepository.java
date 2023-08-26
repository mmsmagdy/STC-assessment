package com.java.stcassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.stcassessment.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

}
