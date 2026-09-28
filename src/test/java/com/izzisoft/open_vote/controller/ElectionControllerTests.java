package com.izzisoft.open_vote.controller;

import com.izzisoft.open_vote.dto.CreateElectionRequest;
import com.izzisoft.open_vote.dto.CreateElectionResponse;
import com.izzisoft.open_vote.model.ElectionStatus;
import com.izzisoft.open_vote.security_config.JwtGenerator;
import com.izzisoft.open_vote.security_config.SecurityConfig;
import com.izzisoft.open_vote.service.ElectionService;
import com.izzisoft.open_vote.service.security.AppUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ElectionController.class)
@Import(SecurityConfig.class)
class ElectionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ElectionService electionService;

    @MockitoBean
    private JwtGenerator jwtGenerator;

    @MockitoBean
    private AppUserDetailsService appUserDetailsService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateStatus() throws Exception {
        Long electionId = 1L;

        mockMvc.perform(put("/election/update/status/{electionId}", electionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "status": "FINISHED"
                                }
                                """))
                .andExpect(status().isOk());

        verify(electionService).updateStatus(electionId, ElectionStatus.FINISHED);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateTitle() throws Exception {
        Long electionId = 1L;

        mockMvc.perform(put("/election/update/title/{electionId}", electionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "newTitle": "Test title"
                                }
                                """))
                .andExpect(status().isOk());

        verify(electionService).updateTitle(electionId, "Test title");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateEndDate() throws Exception {
        Long electionId = 1L;

        mockMvc.perform(put("/election/update/date/end/{electionId}", electionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "newEndDate": "2026-02-03T10:23:00"
                                }
                                """))
                .andExpect(status().isOk());

        verify(electionService).updateEndDate(electionId, LocalDateTime.of(
                2026, 2, 3, 10, 23, 0
        ));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateStarDate() throws Exception {
        Long electionId = 1L;

        mockMvc.perform(put("/election/update/date/start/{electionId}", electionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "newStartDate": "2026-02-03T10:23:00"
                                }
                                """))
                .andExpect(status().isOk());

        verify(electionService).updateStartDate(electionId, LocalDateTime.of(
                2026, 2, 3, 10, 23, 0
        ));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteElection() throws Exception {
        Long electionId = 1L;

        mockMvc.perform(delete("/election/delete/{electionId}", electionId))
                .andExpect(status().isNoContent());

        verify(electionService).deleteElection(electionId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateElection() throws Exception {
        CreateElectionResponse createElectionResponse = new CreateElectionResponse(
                "Election1",
                LocalDateTime.of(2026, 2, 3, 10, 23, 0),
                LocalDateTime.of(2026, 4, 3, 10, 23, 0),
                ElectionStatus.OPENED
        );

        when(electionService.createElection(any(CreateElectionRequest.class)))
                .thenReturn(createElectionResponse);

        mockMvc.perform(post("/election/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Election1",
                                    "startDate": "2026-02-03T10:23:00",
                                    "endDate": "2026-04-03T10:23:00"
                                }
                                """))
                .andExpect(status().isCreated());
    }
}
