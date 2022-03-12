package com.mas.dashboard.repository;

import com.mas.dashboard.entity.DailyWordsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyWordsResponseRepository extends JpaRepository<DailyWordsResponse, Long> {

  Optional<DailyWordsResponse> findByStudentIdAndDailyWordsId (final Long studentId, final Long dailyWordsId);

}
