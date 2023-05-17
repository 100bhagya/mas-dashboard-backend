package com.mas.dashboard.repository;

import com.mas.dashboard.entity.DailyWordsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyWordsResponseRepository extends JpaRepository<DailyWordsResponse, Long> {

  String GET_WORDS_RESPONSE_COMPLETED_STATUS = "select distinct dw.id, dwr.student_id, dwr.completed, cast(dw.date as date)" +
          " from daily_words dw left join daily_words_response dwr on dw.id = dwr.daily_words_id and dwr.student_id = :studentId" +
          " where cast(dw.date as date) >= :fromDate" +
          " and cast(dw.date as date) <= :toDate and dw.deleted = false order by date";

  Optional<DailyWordsResponse> findByStudentIdAndDailyWordsId (final Long studentId, final Long dailyWordsId);

  @Query(nativeQuery = true, value = GET_WORDS_RESPONSE_COMPLETED_STATUS)
  List<Tuple> checkCompletedStatusByStudentIdAndDate (@Param("fromDate") final Date fromDate,
                                                      @Param("toDate") final Date toDate,
                                                      @Param("studentId") final Long studentId);

}
