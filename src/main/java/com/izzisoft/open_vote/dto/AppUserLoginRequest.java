package com.izzisoft.open_vote.dto;

public record AppUserLoginRequest(
        String email,
        String password
) {
}
