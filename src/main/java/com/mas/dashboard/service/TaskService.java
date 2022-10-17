package com.mas.dashboard.service;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.dto.TaskRatingDto;
import com.mas.dashboard.dto.WeeklySummaryDto;
import com.mas.dashboard.entity.DailyWords;
import com.mas.dashboard.entity.DailyWordsResponse;
import com.mas.dashboard.entity.TaskRating;
import com.mas.dashboard.entity.WeeklySummary;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskService {

  List<DailyWords> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWords getDailyWords (final Date date);

  List<DailyWords> getMonthlyWords (final Date startDate, final Date endDate);

  DailyWordsResponse saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponse getDailyWordsResponse (final Long studentId, final Long dailyWordId);

  DailyWordsResponse updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  Map<Date, Boolean> checkDailyWordsCompletedStatus (final Date fromDate, final Date toDate, final Long studentId);

  WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto);

  WeeklySummary getWeeklySummary (final Integer weekNumber, final Integer articleNumber);

  TaskRating createTaskRating (final TaskRatingDto taskRatingDto);

  List<TaskRating> getAllTaskRating (final Long studentId, final String category);

  TaskRating updateTaskRating (final TaskRatingDto taskRatingDto);
}
