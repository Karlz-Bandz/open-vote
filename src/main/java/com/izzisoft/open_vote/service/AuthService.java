package com.izzisoft.open_vote.service;

import com.izzisoft.open_vote.dto.AppUserLoginRequest;
import com.izzisoft.open_vote.dto.AppUserRegisterRequest;
import com.izzisoft.open_vote.dto.AppUserResponse;
import com.izzisoft.open_vote.exception.EmailAlreadyExistsException;
import com.izzisoft.open_vote.model.AppUser;
import com.izzisoft.open_vote.repo.AppUserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final AppUserRepo appUserRepo;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AppUserRepo appUserRepo) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.appUserRepo = appUserRepo;
    }

    public String loginUser(AppUserLoginRequest appUserLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(appUserLoginRequest.email(), appUserLoginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Login success!";
    }

    public AppUserResponse registerUser(AppUserRegisterRequest appUserRegisterRequest) {

        if (appUserRepo.existsByEmail(appUserRegisterRequest.email())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(appUserRegisterRequest.email());
        appUser.setPassword(passwordEncoder.encode(appUserRegisterRequest.password()));

        appUserRepo.save(appUser);

        return new AppUserResponse(appUser.getEmail(), "User register with success!");
    }
}
