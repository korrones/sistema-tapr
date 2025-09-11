package com.example.authservice.interfaces.rest;


import com.example.authservice.application.user.ListUsersHandler;
import com.example.authservice.application.user.RegisterUserHandler;
import com.example.authservice.interfaces.rest.dto.user.RegisterUserRequest;
import com.example.authservice.interfaces.rest.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UserController {

    private final ListUsersHandler listUsersHandler;
    private final RegisterUserHandler registerUserHandler;

    @GetMapping
    @Operation(summary = "Lista os usuários paginados", description = "Retorna uma página com os usuários")
    public ResponseEntity<Page<UserResponse>> list(@Parameter(description = "Informações de paginação") Pageable pageable) {
        Page<UserResponse> page = listUsersHandler.handle(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Operation(summary = "Registra um novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    public ResponseEntity<UserResponse> register(
        @RequestBody(description = "Dados para registro do usuário", required = true) 
        @Valid @org.springframework.web.bind.annotation.RequestBody RegisterUserRequest request) {

        UserResponse created = registerUserHandler.handle(request.name(), request.email(), request.password());

        return ResponseEntity.created(URI.create("/users/" + created.id())).body(created);
    }
}
