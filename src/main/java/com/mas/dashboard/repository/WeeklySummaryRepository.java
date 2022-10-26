package com.mas.dashboard.repository;

import com.mas.dashboard.entity.WeeklySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklySummaryRepository extends JpaRepository<WeeklySummary, Long> {

  Optional<WeeklySummary> findByWeekNumberAndArticleNumberAndDeletedFalse (final Integer weekNumber, final Integer articleNumber);

  List<WeeklySummary> findAll();

  List<WeeklySummary> findByWeekNumber(final Integer weekNumber);

}
