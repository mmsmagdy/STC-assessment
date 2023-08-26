package com.java.stcassessment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum ErrorCode {

    NotFoundItem("IT-NOF", "item not found"),
    NotFoundPermission("PER-NOF", "permission not found"),
    AccessDenied("NOT-AUTH","You don't have permission to create this item.");
    
    private String code;

    private String description;

}
