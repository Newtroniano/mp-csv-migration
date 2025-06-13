package com.luis.multiportal.controllers;
import com.luis.multiportal.dto.UserCreateDTO;
import com.luis.multiportal.dto.UserResponseDTO;
import com.luis.multiportal.services.ApiResponse;
import com.luis.multiportal.services.UserService;
import com.luis.multiportal.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
