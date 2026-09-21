package com.izzisoft.open_vote.controller;

import com.izzisoft.open_vote.dto.AppUserLoginRequest;
import com.izzisoft.open_vote.dto.AppUserRegisterRequest;
import com.izzisoft.open_vote.dto.AppUserResponse;
import com.izzisoft.open_vote.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserResponse> register(@Valid @RequestBody AppUserRegisterRequest appUserRegisterRequest) {
        return new ResponseEntity<>(authService.registerUser(appUserRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUserLoginRequest appUserLoginRequest) {
        return new ResponseEntity<>(authService.loginUser(appUserLoginRequest), HttpStatus.OK);
    }
}
