package com.mas.dashboard.service;

import com.mas.dashboard.dto.*;
import com.mas.dashboard.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskService {

  List<DailyWords> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWords getDailyWords (final Date date);

  DailyWordsResponse saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponse getDailyWordsResponse (final Long studentId, final Long dailyWordId);

  DailyWordsResponse updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  Map<Date, Boolean> checkDailyWordsCompletedStatus (final Date fromDate, final Date toDate, final Long studentId);

  WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto);

  List<WeeklySummary> getWeeklySummary (final Date date);

  TaskRating createTaskRating (final TaskRatingDto taskRatingDto);

  List<TaskRating> getAllTaskRating (final Long studentId, final String category);

  TaskRating updateTaskRating (final TaskRatingDto taskRatingDto);

  WeeklySummaryResponse saveWeeklySummaryResponse (final WeeklySummaryResponseDto file) throws IOException;

}
