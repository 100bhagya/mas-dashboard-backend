package com.mas.dashboard.service.impl;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.dto.WeeklySummaryDto;
import com.mas.dashboard.entity.DailyWords;
import com.mas.dashboard.entity.DailyWordsResponse;
import com.mas.dashboard.entity.WeeklySummary;
import com.mas.dashboard.mapper.DailyWordsMapper;
import com.mas.dashboard.mapper.DailyWordsResponseMapper;
import com.mas.dashboard.mapper.WeeklySummaryMapper;
import com.mas.dashboard.repository.DailyWordRepository;
import com.mas.dashboard.repository.DailyWordsResponseRepository;
import com.mas.dashboard.repository.WeeklySummaryRepository;
import com.mas.dashboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private DailyWordRepository dailyWordRepository;

  @Autowired
  private DailyWordsResponseRepository dailyWordsResponseRepository;

  @Autowired
  private WeeklySummaryRepository weeklySummaryRepository;

  private static final DailyWordsMapper DAILY_WORDS_MAPPER = DailyWordsMapper.INSTANCE;

  private static final DailyWordsResponseMapper DAILY_WORDS_RESPONSE_MAPPER = DailyWordsResponseMapper.INSTANCE;

  private static final WeeklySummaryMapper WEEKLY_SUMMARY_MAPPER = WeeklySummaryMapper.INSTANCE;

  public List<DailyWordsDto> saveDailyWords(List<DailyWordsDto> dailyWordsRequestList) {
    final List<DailyWords> dailyWordsList = new ArrayList<>();

    dailyWordsRequestList.forEach(e -> {
      final Optional<DailyWords> optionalDailyWords = this.dailyWordRepository.findByDate(e.getDate());
      if (optionalDailyWords.isPresent()) {
        throw new IllegalArgumentException("Daily words for the given date already exist");
      }
      DailyWords dailyWords = DAILY_WORDS_MAPPER.toDailyWordsEntity(e);
      dailyWordsList.add(dailyWords);
    });

    this.dailyWordRepository.saveAll(dailyWordsList);
    return DAILY_WORDS_MAPPER.toDailyWordsDto(dailyWordsList);
  }

  public DailyWordsDto getDailyWords (final Long studentId, final Date date) {
    final Optional<DailyWords> optionalDailyWords = this.dailyWordRepository.findByDate(date);
    if (!optionalDailyWords.isPresent()) {
      throw new IllegalArgumentException("Daily words for the given date not found");
    }
    return DAILY_WORDS_MAPPER.toDailyWordsDto(optionalDailyWords.get());
  }

  public DailyWordsResponseDto saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto) {
    final DailyWordsResponse dailyWordsResponse = DAILY_WORDS_RESPONSE_MAPPER.toDailyWordsResponseEntity(dailyWordsResponseDto);
    if (dailyWordsResponseDto.getResponseOne().isEmpty() || dailyWordsResponseDto.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setCompleted(Boolean.FALSE);
    } else {
      dailyWordsResponse.setCompleted(Boolean.TRUE);
    }
    this.dailyWordsResponseRepository.save(dailyWordsResponse);
    return DAILY_WORDS_RESPONSE_MAPPER.toDailyWordsResponseDto(dailyWordsResponse);
  }

  public DailyWordsResponseDto getDailyWordsResponse (final Long studentId, final Long dailyWordsId) {
    final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(studentId, dailyWordsId);
    if (!optionalDailyWordsResponse.isPresent()) {
      return null;
    }
    final DailyWordsResponse dailyWordsResponse = optionalDailyWordsResponse.get();
    return DAILY_WORDS_RESPONSE_MAPPER.toDailyWordsResponseDto(dailyWordsResponse);
  }

  public DailyWordsResponseDto updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto) {
    final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(dailyWordsResponseDto.getStudentId(),
        dailyWordsResponseDto.getDailyWordsId());
    if (!optionalDailyWordsResponse.isPresent()) {
      throw new IllegalArgumentException("Daily words response not found for the given student and word id");
    }
    final DailyWordsResponse dailyWordsResponse = optionalDailyWordsResponse.get();
    if (!dailyWordsResponseDto.getResponseOne().isEmpty()) {
      dailyWordsResponse.setResponseOne(dailyWordsResponse.getResponseOne());
    }
    if (!dailyWordsResponseDto.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setResponseTwo(dailyWordsResponseDto.getResponseTwo());
    }
    if (!dailyWordsResponse.getResponseOne().isEmpty() || !dailyWordsResponse.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setCompleted(Boolean.TRUE);
    }
    return DAILY_WORDS_RESPONSE_MAPPER.toDailyWordsResponseDto(dailyWordsResponse);
  }

  public Map<Integer, Boolean> checkDailyWordsCompletedStatus (final Integer month, final Integer year) {
    final Date date = new Date();
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    int todayMonth = localDate.getMonthValue();
    int todayYear = localDate.getYear();
    int todayDay = localDate.getDayOfMonth();
    // TODO: Write logic for this method
    return null;
  }

  public WeeklySummaryDto saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto) {
    final Integer weeklySummaryCountByDate = this.weeklySummaryRepository.findCountByDateAndDeletedFalse(weeklySummaryDto.getDate());
    if (weeklySummaryCountByDate >= 2) {
      throw new IllegalArgumentException("Weekly summary already exists for the given date");
    }
    this.weeklySummaryRepository.save(WEEKLY_SUMMARY_MAPPER.toWeeklySummaryEntity(weeklySummaryDto));
    return weeklySummaryDto;
  }

  public List<WeeklySummaryDto> getWeeklySummary (final Date date) {
    final Optional<List<WeeklySummary>> optionalWeeklySummaryDtoList = this.weeklySummaryRepository.
        findAllByDateAndDeletedFalse(date);
    if (!optionalWeeklySummaryDtoList.isPresent()) {
      throw new IllegalArgumentException("Weekly Summary not found for the given date");
    }
    final List<WeeklySummary> weeklySummaries = optionalWeeklySummaryDtoList.get();
    return WEEKLY_SUMMARY_MAPPER.toWeeklySummaryDtoList(weeklySummaries);
  }
}
