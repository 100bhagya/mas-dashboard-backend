package com.mas.dashboard.service;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.dto.WeeklySummaryDto;
import com.mas.dashboard.entity.DailyWords;
import com.mas.dashboard.entity.DailyWordsResponse;
import com.mas.dashboard.entity.WeeklySummary;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskService {

  List<DailyWords> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWords getDailyWords (final Date date);

  DailyWordsResponse saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponse getDailyWordsResponse (final Long studentId, final Long dailyWordId);

  DailyWordsResponse updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  Map<Integer, Boolean> checkDailyWordsCompletedStatus (final Integer monthName, final Integer year);

  WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto);

  List<WeeklySummary> getWeeklySummary (final Date date);
}
