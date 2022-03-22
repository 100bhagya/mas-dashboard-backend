package com.mas.dashboard.repository;

import com.mas.dashboard.entity.WeeklySummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeeklySummaryResponseRepository extends JpaRepository<WeeklySummaryResponseRepository, String> {
    Optional<WeeklySummaryResponse> getFile(String fileName);
}
