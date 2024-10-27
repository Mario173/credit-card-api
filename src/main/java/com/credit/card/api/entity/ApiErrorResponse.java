package com.credit.card.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * API error response entity - used to return error messages
 * in a consistent format
 */
@AllArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
