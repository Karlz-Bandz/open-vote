package com.izzisoft.open_vote.service;

import com.izzisoft.open_vote.dto.CreateElectionRequest;
import com.izzisoft.open_vote.dto.CreateElectionResponse;
import com.izzisoft.open_vote.model.ElectionStatus;

import java.time.LocalDateTime;

public interface ElectionService {

    CreateElectionResponse createElection(CreateElectionRequest createElectionRequest);

    void deleteElection(Long electionId);

    void updateStartDate(Long electionId, LocalDateTime newStartDate);

    void updateEndDate(Long electionId, LocalDateTime newEndDate);

    void updateTitle(Long electionId, String newTitle);

    void updateStatus(Long electionId, ElectionStatus newStatus);
}
