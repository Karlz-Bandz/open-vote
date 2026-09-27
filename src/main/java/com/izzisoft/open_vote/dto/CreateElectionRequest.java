package com.izzisoft.open_vote.dto;

import java.time.LocalDateTime;

public record CreateElectionRequest(
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
