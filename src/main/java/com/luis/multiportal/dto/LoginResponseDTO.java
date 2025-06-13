package com.luis.multiportal.dto;

public class LoginResponseDTO {
    private String username;
    private String token;
    private String message;


    public LoginResponseDTO(String username, String token, String message) {
        this.username = username;
        this.token = token;
        this.message = message;
    }

    public String getUsername() { return username; }
    public String getToken() { return token; }
    public String getMessage() { return message; }
}