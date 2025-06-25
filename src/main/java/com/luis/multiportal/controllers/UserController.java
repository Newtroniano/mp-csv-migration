package com.luis.multiportal.controllers;
import com.luis.multiportal.dto.LoginRequestDTO;
import com.luis.multiportal.dto.LoginResponseDTO;
import com.luis.multiportal.dto.UserCreateDTO;
import com.luis.multiportal.dto.UserResponseDTO;
import com.luis.multiportal.exceptions.LoginException;
import com.luis.multiportal.services.UserService;
import com.luis.multiportal.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@Valid @RequestBody UserCreateDTO dto) {
        User user = new User();
        user.setUser(dto.getUser());
        user.setPassword(dto.getPassword());

        User created = userService.create(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        UserResponseDTO responseDTO = new UserResponseDTO(Math.toIntExact(created.getId()), created.getUser());

        ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                201,
                "Usuário criado com sucesso",
                responseDTO
        );

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO dto) {
        try {
            LoginResponseDTO responseDTO = userService.login(dto.getUser(), dto.getPassword());

            ResponseCookie cookie = ResponseCookie.from("token", responseDTO.getToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();


            ApiResponse<LoginResponseDTO> response = new ApiResponse<>(
                    200,
                    "Login realizado com sucesso",
                    responseDTO
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);

        } catch (LoginException e) {
            ApiResponse<LoginResponseDTO> errorResponse = new ApiResponse<>(
                    401,
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<?> teste() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado");
        }
        return ResponseEntity.ok("Usuário logado: " + auth.getName());
    }

}
