package com.izzisoft.open_vote.dto;

import java.time.LocalDateTime;

public record UpdateEndDateRequest(
        LocalDateTime newEndDate
) {
}
