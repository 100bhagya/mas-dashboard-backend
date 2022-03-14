package com.mas.dashboard.service;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.dto.WeeklySummaryDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskService {

  List<DailyWordsDto> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWordsDto getDailyWords (final Long studentId, final Date date);

  DailyWordsResponseDto saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponseDto getDailyWordsResponse (final Long studentId, final Long dailyWordId);

  DailyWordsResponseDto updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  Map<Integer, Boolean> checkDailyWordsCompletedStatus (final Integer monthName, final Integer year);

  WeeklySummaryDto saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto);

  List<WeeklySummaryDto> getWeeklySummary (final Date date);
}
