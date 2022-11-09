package com.mas.dashboard.service;

import com.mas.dashboard.dto.*;
import com.mas.dashboard.entity.*;

import java.util.*;

public interface TaskService {

  List<DailyWords> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWords getDailyWords (final Date date);

  List<DailyWords> getMonthlyWords (final Date startDate, final Date endDate);

  DailyWordsResponse saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponse getDailyWordsResponse (final Long dailyWordId);

  DailyWordsResponse updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  Map<Date, List<Boolean>> checkDailyWordsResponseStatus (final Date fromDate, final Date toDate);

  WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto);

  WeeklySummary getWeeklySummary (final Integer weekNumber, final Integer articleNumber);

  Map<Integer, boolean[]> weeklySummaryResponseStatus();

  WeeklySummaryResponse saveWeeklySummaryResponse (final WeeklySummaryResponseDto weeklySummaryResponseDto);

  WeeklySummaryResponse getWeeklySummaryResponse (final Long weeklySummaryId);

  WeeklySummaryResponse updateWeeklySummaryResponse (final WeeklySummaryResponseDto weeklySummaryResponseDto);

  TaskRating createTaskRating (final TaskRatingDto taskRatingDto);

  List<TaskRating> getAllTaskRating (final String category);

  TaskRating updateTaskRating (final TaskRatingDto taskRatingDto);
}
