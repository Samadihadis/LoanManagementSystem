package com.samadihadis.loanmanagementsystem.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private String message;
    private int status;
    private String error;
    private String path;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, int status, String error, String path) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
