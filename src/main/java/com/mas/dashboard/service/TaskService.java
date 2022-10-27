package com.mas.dashboard.service;

import com.mas.dashboard.dto.*;
import com.mas.dashboard.entity.*;

import java.util.*;

public interface TaskService {

  List<DailyWords> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWords getDailyWords (final Date date);

  List<DailyWords> getMonthlyWords (final Date startDate, final Date endDate);

  DailyWordsResponse saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponse getDailyWordsResponse (final Long studentId, final Long dailyWordId);

  DailyWordsResponse updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  Map<Date, List<Boolean>> checkDailyWordsResponseStatus (final Date fromDate, final Date toDate, final Long studentId);

  WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto);

  WeeklySummary getWeeklySummary (final Integer weekNumber, final Integer articleNumber);

//  List<WeeklySummary> getAllWeeklySummary();
//
//  List<WeeklySummary> getWeeklySummaryByWeek(Integer weekNumber);

  Map<Integer, List<Boolean>> weeklySummaryResponseStatus(final Long StudentId);

  WeeklySummaryResponse saveWeeklySummaryResponse (final WeeklySummaryResponseDto weeklySummaryResponseDto);

  WeeklySummaryResponse getWeeklySummaryResponse (final Long studentId, final Long weeklySummaryId);

  WeeklySummaryResponse updateWeeklySummaryResponse (final WeeklySummaryResponseDto weeklySummaryResponseDto);

  TaskRating createTaskRating (final TaskRatingDto taskRatingDto);

  List<TaskRating> getAllTaskRating (final Long studentId, final String category);

  TaskRating updateTaskRating (final TaskRatingDto taskRatingDto);
}
