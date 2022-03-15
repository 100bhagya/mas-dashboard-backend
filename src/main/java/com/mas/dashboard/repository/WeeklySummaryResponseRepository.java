package com.mas.dashboard.repository;

import com.mas.dashboard.entity.WeeklySummary;
import com.mas.dashboard.entity.WeeklySummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklySummaryResponseRepository extends JpaRepository<WeeklySummaryResponse, Long> {
    Optional<WeeklySummaryResponse> findByArticleId (final Long articleId);
}
