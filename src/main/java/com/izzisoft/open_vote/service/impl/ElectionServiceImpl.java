package com.izzisoft.open_vote.service.impl;

import com.izzisoft.open_vote.dto.CreateElectionRequest;
import com.izzisoft.open_vote.dto.CreateElectionResponse;
import com.izzisoft.open_vote.exception.ElectionNotFoundException;
import com.izzisoft.open_vote.exception.TitleAlreadyExistsException;
import com.izzisoft.open_vote.model.Election;
import com.izzisoft.open_vote.model.ElectionStatus;
import com.izzisoft.open_vote.repo.ElectionRepo;
import com.izzisoft.open_vote.service.ElectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepo electionRepo;

    public ElectionServiceImpl(ElectionRepo electionRepo) {
        this.electionRepo = electionRepo;
    }

    @Override
    public CreateElectionResponse createElection(CreateElectionRequest createElectionRequest) {

        checkTitleDuplicate(createElectionRequest.title());

        Election election = new Election();
        election.setTitle(createElectionRequest.title());
        election.setStartDate(createElectionRequest.startDate());
        election.setEndDate(createElectionRequest.endDate());
        election.setElectionStatus(ElectionStatus.OPENED);
        electionRepo.save(election);

        return new CreateElectionResponse(
                election.getTitle(),
                election.getStartDate(),
                election.getEndDate(),
                election.getElectionStatus()
        );
    }

    @Override
    public void deleteElection(Long electionId) {
        electionRepo.deleteById(electionId);
    }

    @Transactional
    @Override
    public void updateStartDate(Long electionId, LocalDateTime newStartDate) {
        Election foundElection = electionRepo.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found!"));

        foundElection.setStartDate(newStartDate);
    }

    @Transactional
    @Override
    public void updateEndDate(Long electionId, LocalDateTime newEndDate) {
        Election foundElection = electionRepo.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found!"));

        foundElection.setEndDate(newEndDate);
    }

    @Transactional
    @Override
    public void updateTitle(Long electionId, String newTitle) {

        checkTitleDuplicate(newTitle);

        Election foundElection = electionRepo.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found!"));

        foundElection.setTitle(newTitle);
    }

    @Transactional
    @Override
    public void updateStatus(Long electionId, ElectionStatus newStatus) {
        Election foundElection = electionRepo.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found!"));

        foundElection.setElectionStatus(newStatus);
    }

    private void checkTitleDuplicate(String newTitle) {
        if (electionRepo.existsByTitle(newTitle)) {
            throw new TitleAlreadyExistsException("Title already exists!");
        }
    }
}
