package com.mas.dashboard.repository;

import com.mas.dashboard.entity.WeeklySummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklySummaryResponseRepository extends JpaRepository<WeeklySummaryResponse, Long> {

    Optional<WeeklySummaryResponse> findByStudentIdAndWeeklySummaryId (final Long studentId, final Long weeklySummaryId);

}
