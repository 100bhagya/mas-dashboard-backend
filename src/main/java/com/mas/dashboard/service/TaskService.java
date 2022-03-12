package com.mas.dashboard.service;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;

import java.util.Date;
import java.util.List;

public interface TaskService {

  List<DailyWordsDto> saveDailyWords (final List<DailyWordsDto> dailyWordsRequests);

  DailyWordsDto getDailyWords (final Long studentId, final Date date);

  DailyWordsResponseDto saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponseDto getDailyWordsResponse (final Long studentId, final Long dailyWordId);

  DailyWordsResponseDto updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto);
}
