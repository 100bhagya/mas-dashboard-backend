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

  String GET_WORDS_RESPONSE_COMPLETED_STATUS = "select dwr.student_id as studentId, dwr.completed as completed, dw.date as date" +
      " from daily_words_response dwr inner join daily_words dw on dw.id = dwr.daily_words_id" +
      " where dwr.student_id = :studentId and dw.date >= :fromDate and dw.date <= :toDate and dw.deleted = false";

  Optional<DailyWordsResponse> findByStudentIdAndDailyWordsId (final Long studentId, final Long dailyWordsId);




  @Query(nativeQuery = true, value = GET_WORDS_RESPONSE_COMPLETED_STATUS)
  List<Tuple> checkCompletedStatusByStudentIdAndDate (@Param("fromDate") final Date fromDate,
                                                      @Param("toDate") final Date toDate,
                                                      @Param("studentId") final Long studentId);

}
