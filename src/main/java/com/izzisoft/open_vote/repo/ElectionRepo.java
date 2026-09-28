package com.izzisoft.open_vote.repo;

import com.izzisoft.open_vote.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepo extends JpaRepository<Election, Long> {
    boolean existsByTitle(String title);
}
