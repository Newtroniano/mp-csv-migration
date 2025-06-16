package com.luis.multiportal.controllers.exeptions;

import com.luis.multiportal.controllers.ApiResponse;
import com.luis.multiportal.exceptions.LoginException;
import com.luis.multiportal.exceptions.UserCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<Void> response = new ApiResponse<>(400, message, null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserCreationException(UserCreationException ex) {
        ApiResponse<Void> response = new ApiResponse<>(400, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(500, "Erro interno: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiResponse<Void>> handleLoginException(LoginException ex) {
        ApiResponse<Void> response = new ApiResponse<>(401, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


}
