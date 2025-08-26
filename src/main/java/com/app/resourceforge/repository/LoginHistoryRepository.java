package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.LoginHistory;
import java.util.Optional;


public interface LoginHistoryRepository extends JpaRepository<LoginHistory,Long> {

    Optional<LoginHistory> findBySessionId(String sessionId);
    
}
