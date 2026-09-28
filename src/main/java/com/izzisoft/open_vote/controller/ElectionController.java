package com.izzisoft.open_vote.controller;

import com.izzisoft.open_vote.dto.CreateElectionRequest;
import com.izzisoft.open_vote.dto.CreateElectionResponse;
import com.izzisoft.open_vote.dto.UpdateElectionStatusRequest;
import com.izzisoft.open_vote.dto.UpdateEndDateRequest;
import com.izzisoft.open_vote.dto.UpdateStartDateRequest;
import com.izzisoft.open_vote.dto.UpdateTitleRequest;
import com.izzisoft.open_vote.service.ElectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/election")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateElectionResponse> createElection(@RequestBody CreateElectionRequest createElectionRequest) {
        return new ResponseEntity<>(electionService.createElection(createElectionRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{electionId}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long electionId) {
        electionService.deleteElection(electionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/date/start/{electionId}")
    public ResponseEntity<Void> updateStartDate(@PathVariable Long electionId, @RequestBody UpdateStartDateRequest request) {
        electionService.updateStartDate(electionId, request.newStartDate());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/date/end/{electionId}")
    public ResponseEntity<Void> updateEndDate(@PathVariable Long electionId, @RequestBody UpdateEndDateRequest request) {
        electionService.updateEndDate(electionId, request.newEndDate());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/title/{electionId}")
    public ResponseEntity<Void> updateTitle(@PathVariable Long electionId, @RequestBody UpdateTitleRequest request) {
        electionService.updateTitle(electionId, request.newTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/status/{electionId}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long electionId, @RequestBody UpdateElectionStatusRequest request) {
        electionService.updateStatus(electionId, request.status());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
