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

  @Query(nativeQuery = true, value = "select count(id) from weekly_summary where date = :date and deleted = false")
  Integer findCountByDateAndDeletedFalse (@Param("date") final Date date);

  @Query(nativeQuery = true, value = "select * from weekly_summary where date = :date")
  Optional<List<WeeklySummary>> findAllByDateAndDeletedFalse(@RequestParam("date") final Date date);
}
