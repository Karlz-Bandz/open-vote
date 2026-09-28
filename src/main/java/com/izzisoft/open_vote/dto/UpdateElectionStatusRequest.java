package com.izzisoft.open_vote.dto;

import com.izzisoft.open_vote.model.ElectionStatus;

public record UpdateElectionStatusRequest(
        ElectionStatus status
) {
}
