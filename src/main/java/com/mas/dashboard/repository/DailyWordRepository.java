package com.mas.dashboard.repository;

import com.mas.dashboard.entity.DailyWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyWordRepository extends JpaRepository<DailyWords, Long> {

  Optional<DailyWords> findByDate (final Date date);

  List<DailyWords> findByDateBetween (final Date startDate, final Date endDate);


  String GET_DAILY_WORDS_RESPONSE_COMPLETED_STATUS = "SELECT dw.date, d.completed FROM daily_words dw INNER JOIN daily_words_response d ON d.daily_words_id = dw.id WHERE dw.date BETWEEN :fromDate AND :toDate AND d.student_id = :studentId";

  @Query(nativeQuery = true, value = GET_DAILY_WORDS_RESPONSE_COMPLETED_STATUS)
  List<Object[]> findDateAndStatusByStudentIdAndDateBetween(@Param("studentId") Long studentId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);



}
