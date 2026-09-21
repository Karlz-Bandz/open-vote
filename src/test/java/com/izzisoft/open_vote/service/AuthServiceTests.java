package com.izzisoft.open_vote.service;

import com.izzisoft.open_vote.dto.AppUserLoginRequest;
import com.izzisoft.open_vote.dto.AppUserRegisterRequest;
import com.izzisoft.open_vote.dto.AppUserResponse;
import com.izzisoft.open_vote.model.AppUser;
import com.izzisoft.open_vote.repo.AppUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppUserRepo appUserRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldLoginUser() {
        Authentication authentication = mock(Authentication.class);

        AppUserLoginRequest appUserLoginRequest = new AppUserLoginRequest(
                "test@mail.com",
                "password"
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String result = authService.loginUser(appUserLoginRequest);

        assertEquals("Login success!", result);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void shouldRegisterUser() {
        AppUserRegisterRequest appUserRegisterRequest = new AppUserRegisterRequest(
                "test@mail.com",
                "password"
        );

        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(appUserRepo.existsByEmail("test@mail.com")).thenReturn(false);

        AppUserResponse response = authService.registerUser(appUserRegisterRequest);

        verify(appUserRepo).save(any(AppUser.class));

        assertEquals("test@mail.com", response.email());
    }
}
