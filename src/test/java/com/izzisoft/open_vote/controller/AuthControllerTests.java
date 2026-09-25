package com.izzisoft.open_vote.controller;

import com.izzisoft.open_vote.config.SecurityConfig;
import com.izzisoft.open_vote.dto.AppUserLoginRequest;
import com.izzisoft.open_vote.dto.AppUserRegisterRequest;
import com.izzisoft.open_vote.dto.AppUserResponse;
import com.izzisoft.open_vote.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void shouldLoginUser() throws Exception {
        when(authService.loginUser(any(AppUserLoginRequest.class)))
                .thenReturn("Login success!");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@test.com",
                                    "password": "password"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        AppUserResponse response =
                new AppUserResponse(
                        "test@test.com",
                        "User register with success!"
                );

        when(authService.registerUser(any(AppUserRegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@test.com",
                                    "password": "password"
                                }
                                """))
                .andExpect(status().isCreated());
    }
}
