package com.izzisoft.open_vote.dto;

import com.izzisoft.open_vote.model.ElectionStatus;

import java.time.LocalDateTime;

public record CreateElectionResponse(
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ElectionStatus status
) {
}
