package com.mas.dashboard.repository;

import com.mas.dashboard.entity.DailyWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyWordRepository extends JpaRepository<DailyWords, Long> {

  Optional<DailyWords> findByDate (final Date date);

  List<DailyWords> findByDateBetween (final Date startDate, final Date endDate);
}
