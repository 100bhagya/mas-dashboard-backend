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

import java.text.SimpleDateFormat;
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

  public List<DailyWords> saveDailyWords(List<DailyWordsDto> dailyWordsRequestList) {
    final List<DailyWords> dailyWordsList = new ArrayList<>();

    dailyWordsRequestList.forEach(e -> {
      final Date date;
      try {
        date = new SimpleDateFormat("dd-MM-yyyy").parse(e.getDate());
      } catch (Exception exception) {
        throw new IllegalArgumentException("Date format error");
      }
      final Optional<DailyWords> optionalDailyWords = this.dailyWordRepository.findByDate(date);
      if (optionalDailyWords.isPresent()) {
        throw new IllegalArgumentException("Daily words for the given date already exist");
      }
      // Todo: Implement a mapper
      // DailyWords dailyWords = DAILY_WORDS_MAPPER.toDailyWordsEntity(e);
      DailyWords dailyWords = new DailyWords();
      dailyWords.setWordOne(e.getWordOne());;
      dailyWords.setWordOneCat(e.getWordOneCat());;
      dailyWords.setWordOneMeaning(e.getWordOneMeaning());
      dailyWords.setWordTwo(e.getWordTwo());
      dailyWords.setWordTwoCat(e.getWordTwoCat());
      dailyWords.setWordTwoMeaning(e.getWordTwoMeaning());
      dailyWords.setDate(date);
      dailyWords.setCreatedBy(-1L);
      dailyWords.setCreatedDate(date);
      dailyWords.setUpdatedBy(-1L);
      dailyWords.setUpdatedDate(date);
      dailyWordsList.add(dailyWords);
    });
    return this.dailyWordRepository.saveAll(dailyWordsList);
  }

  public DailyWords getDailyWords (final Date date) {
    final Optional<DailyWords> optionalDailyWords = this.dailyWordRepository.findByDate(date);
    if (!optionalDailyWords.isPresent()) {
      throw new IllegalArgumentException("Daily words for the given date not found");
    }
    return optionalDailyWords.get();
  }

  public DailyWordsResponse saveDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto) {
    final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.
        findByStudentIdAndDailyWordsId(dailyWordsResponseDto.getStudentId(), dailyWordsResponseDto.getDailyWordsId());
    if (optionalDailyWordsResponse.isPresent()) {
      throw new IllegalArgumentException("Daily words response already exists");
    }
    final DailyWordsResponse dailyWordsResponse = new DailyWordsResponse();
    dailyWordsResponse.setDailyWordsId(dailyWordsResponseDto.getDailyWordsId());
    dailyWordsResponse.setStudentId(dailyWordsResponseDto.getStudentId());
    dailyWordsResponse.setResponseOne(dailyWordsResponseDto.getResponseOne());
    dailyWordsResponse.setResponseTwo(dailyWordsResponseDto.getResponseTwo());
    dailyWordsResponse.setCreatedBy(dailyWordsResponseDto.getStudentId());
    dailyWordsResponse.setCreatedDate(new Date());
    dailyWordsResponse.setUpdatedBy(dailyWordsResponseDto.getStudentId());
    dailyWordsResponse.setUpdatedDate(new Date());
    if (dailyWordsResponseDto.getResponseOne().isEmpty() || dailyWordsResponseDto.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setCompleted(Boolean.FALSE);
    } else {
      dailyWordsResponse.setCompleted(Boolean.TRUE);
    }
    return this.dailyWordsResponseRepository.save(dailyWordsResponse);
  }

  public DailyWordsResponse getDailyWordsResponse (final Long studentId, final Long dailyWordsId) {
    final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(studentId, dailyWordsId);
    if (!optionalDailyWordsResponse.isPresent()) {
      return null;
    }
    return optionalDailyWordsResponse.get();
  }

  public DailyWordsResponse updateDailyWordsResponse (final DailyWordsResponseDto dailyWordsResponseDto) {
    final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(dailyWordsResponseDto.getStudentId(),
        dailyWordsResponseDto.getDailyWordsId());
    if (!optionalDailyWordsResponse.isPresent()) {
      throw new IllegalArgumentException("Daily words response not found for the given student and word id");
    }
    final DailyWordsResponse dailyWordsResponse = optionalDailyWordsResponse.get();
    if (!dailyWordsResponseDto.getResponseOne().isEmpty()) {
      dailyWordsResponse.setResponseOne(dailyWordsResponseDto.getResponseOne());
    }
    if (!dailyWordsResponseDto.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setResponseTwo(dailyWordsResponseDto.getResponseTwo());
    }
    if (!dailyWordsResponse.getResponseOne().isEmpty() || !dailyWordsResponse.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setCompleted(Boolean.TRUE);
    }
    return this.dailyWordsResponseRepository.save(dailyWordsResponse);
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

  public WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto) {
    final Date date;
    try {
      date = new SimpleDateFormat("dd-MM-yyyy").parse(weeklySummaryDto.getDate());
    } catch (Exception exception) {
      throw new IllegalArgumentException("Date format error");
    }
    final Integer weeklySummaryCountByDate = this.weeklySummaryRepository.findCountByDateAndDeletedFalse(date);
    if (weeklySummaryCountByDate >= 2) {
      throw new IllegalArgumentException("Weekly summary already exists for the given date");
    }
    final WeeklySummary weeklySummary = new WeeklySummary();
    weeklySummary.setArticleTopic(weeklySummaryDto.getArticleTopic());
    weeklySummary.setArticleText(weeklySummaryDto.getArticleText());
    weeklySummary.setReadTime(weeklySummaryDto.getReadTime());
    weeklySummary.setDate(date);
    weeklySummary.setCreatedBy(-1L);
    weeklySummary.setCreatedDate(new Date());
    weeklySummary.setUpdatedBy(-1L);
    weeklySummary.setUpdatedDate(new Date());
    return this.weeklySummaryRepository.save(weeklySummary);
  }

  public List<WeeklySummary> getWeeklySummary (final Date date) {
    final Optional<List<WeeklySummary>> optionalWeeklySummaryDtoList = this.weeklySummaryRepository.
        findAllByDateAndDeletedFalse(date);
    if (!optionalWeeklySummaryDtoList.isPresent()) {
      throw new IllegalArgumentException("Weekly Summary not found for the given date");
    }
    return optionalWeeklySummaryDtoList.get();
  }
}
