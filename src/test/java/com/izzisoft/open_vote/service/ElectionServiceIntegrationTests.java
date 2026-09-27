package com.izzisoft.open_vote.service;

import com.izzisoft.open_vote.dto.CreateElectionRequest;
import com.izzisoft.open_vote.dto.CreateElectionResponse;
import com.izzisoft.open_vote.model.Election;
import com.izzisoft.open_vote.model.ElectionStatus;
import com.izzisoft.open_vote.repo.ElectionRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ElectionServiceIntegrationTests {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private ElectionRepo electionRepo;

    @Test
    void createElectionTest() {
        CreateElectionRequest createElectionRequest = new CreateElectionRequest(
                "Title",
                LocalDateTime.of(2026, 10, 1, 10, 0),
                LocalDateTime.of(2026, 10, 10, 20, 0)
        );

        CreateElectionResponse createElectionResponse = electionService.createElection(createElectionRequest);

        assertEquals("Title", createElectionResponse.title());
        assertEquals(1, electionRepo.count());
    }

    @Test
    void deleteElectionTest() {
        electionService.deleteElection(2L);
        Optional<Election> foundElection = electionRepo.findById(2L);
        assertEquals(Optional.empty(), foundElection);
    }

    @Test
    void updateStartDateTest() {
        LocalDateTime newDate = LocalDateTime.of(2025, 10, 3, 10, 10);

        electionService.updateStartDate(1L, newDate);

        Election foundElection = electionRepo.findById(1L)
                .orElseThrow();

        assertEquals(newDate, foundElection.getStartDate());
    }

    @Test
    void updateEndDateTest() {
        LocalDateTime newDate = LocalDateTime.of(2025, 10, 3, 10, 10);

        electionService.updateEndDate(1L, newDate);

        Election foundElection = electionRepo.findById(1L)
                .orElseThrow();

        assertEquals(newDate, foundElection.getEndDate());
    }

    @Test
    void updateTitleTest() {
        String newTitle = "test66";

        electionService.updateTitle(1L, newTitle);

        Election foundElection = electionRepo.findById(1L)
                .orElseThrow();

        assertEquals(newTitle, foundElection.getTitle());
    }

    @Test
    void updateStatusTest() {
        ElectionStatus newStatus = ElectionStatus.FINISHED;

        electionService.updateStatus(1L, newStatus);

        Election foundElection = electionRepo.findById(1L)
                .orElseThrow();

        assertEquals(newStatus, foundElection.getElectionStatus());
    }
}
