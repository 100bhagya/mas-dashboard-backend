package com.mas.dashboard.service.impl;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.entity.DailyWords;
import com.mas.dashboard.entity.DailyWordsResponse;
import com.mas.dashboard.mapper.DailyWordsMapper;
import com.mas.dashboard.mapper.DailyWordsResponseMapper;
import com.mas.dashboard.repository.DailyWordRepository;
import com.mas.dashboard.repository.DailyWordsResponseRepository;
import com.mas.dashboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private DailyWordRepository dailyWordRepository;

  @Autowired
  private DailyWordsResponseRepository dailyWordsResponseRepository;

  private static final DailyWordsMapper DAILY_WORDS_MAPPER = DailyWordsMapper.INSTANCE;

  private static final DailyWordsResponseMapper DAILY_WORDS_RESPONSE_MAPPER = DailyWordsResponseMapper.INSTANCE;

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
}
