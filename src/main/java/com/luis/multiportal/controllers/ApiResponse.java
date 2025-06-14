package com.luis.multiportal.controllers;

import lombok.Setter;

import java.time.Instant;

public class ApiResponse<T> {
    private Instant timestamp;
    @Setter
    private int status;
    @Setter
    private String message;
    @Setter
    private T data;

    public ApiResponse() {
        this.timestamp = Instant.now();
    }

    public ApiResponse(int status, String message, T data) {
        this();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters e setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}

