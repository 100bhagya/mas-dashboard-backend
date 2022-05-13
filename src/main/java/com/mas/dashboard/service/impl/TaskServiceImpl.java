package com.mas.dashboard.service.impl;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.dto.TaskRatingDto;
import com.mas.dashboard.dto.WeeklySummaryDto;
import com.mas.dashboard.entity.DailyWords;
import com.mas.dashboard.entity.DailyWordsResponse;
import com.mas.dashboard.entity.TaskRating;
import com.mas.dashboard.entity.WeeklySummary;
import com.mas.dashboard.repository.DailyWordRepository;
import com.mas.dashboard.repository.DailyWordsResponseRepository;
import com.mas.dashboard.repository.TaskRatingRepository;
import com.mas.dashboard.repository.WeeklySummaryRepository;
import com.mas.dashboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private DailyWordRepository dailyWordRepository;

  @Autowired
  private DailyWordsResponseRepository dailyWordsResponseRepository;

  @Autowired
  private WeeklySummaryRepository weeklySummaryRepository;

  @Autowired
  private TaskRatingRepository taskRatingRepository;

  String GD = "GD";

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

  public Map<Date, Boolean> checkDailyWordsCompletedStatus (final Date fromDate, final Date toDate, final Long studentId) {
    final List<Tuple> tuples = this.dailyWordsResponseRepository.checkCompletedStatusByStudentIdAndDate(fromDate, toDate, studentId);
    Map<Date, Boolean> dateCompletedStatusMap = new HashMap<>();
    tuples.forEach(tuple -> {
      dateCompletedStatusMap.put((Date) tuple.get("date"), (Boolean) tuple.get("completed"));
    });
    return dateCompletedStatusMap;
  }

  public WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto) {
    final Optional<WeeklySummary> optionalWeeklySummary = this.weeklySummaryRepository.
            findByWeekNumberAndArticleNumberAndDeletedFalse(weeklySummaryDto.getWeekNumber(), weeklySummaryDto.getArticleNumber());
    if (optionalWeeklySummary.isPresent() ) {
      throw new IllegalArgumentException("Weekly summary already exists for the given week");
    }
    final WeeklySummary weeklySummary = new WeeklySummary();
    weeklySummary.setArticleTopic(weeklySummaryDto.getArticleTopic());
    weeklySummary.setArticleText(weeklySummaryDto.getArticleText());
    weeklySummary.setReadTime(weeklySummaryDto.getReadTime());
    weeklySummary.setWeekNumber(weeklySummary.getWeekNumber());
    weeklySummary.setArticleNumber(weeklySummary.getArticleNumber());
    weeklySummary.setCreatedBy(-1L);
    weeklySummary.setCreatedDate(new Date());
    weeklySummary.setUpdatedBy(-1L);
    weeklySummary.setUpdatedDate(new Date());
    return this.weeklySummaryRepository.save(weeklySummary);
  }

  public WeeklySummary getWeeklySummary (final Integer weekNumber, final Integer articleNumber) {
    final Optional<WeeklySummary> optionalWeeklySummary = this.weeklySummaryRepository.
        findByWeekNumberAndArticleNumberAndDeletedFalse(weekNumber, articleNumber);
    if (!optionalWeeklySummary.isPresent()) {
      throw new IllegalArgumentException("Weekly Summary not found for the given week and article number");
    }
    return optionalWeeklySummary.get();
  }

  public TaskRating createTaskRating (final TaskRatingDto taskRatingDto) {
    final Optional<TaskRating> optionalTaskRating = this.taskRatingRepository.
        findByStudentIdAndCategoryAndChapterAndDeletedFalse(taskRatingDto.getStudentId(),
            taskRatingDto.getCategory(), taskRatingDto.getChapter());
    if (optionalTaskRating.isPresent()) {
      throw new IllegalArgumentException("Task rating already exists for the given student id, category and chapter");
    }
    TaskRating taskRating = new TaskRating();
    taskRating.setCategory(taskRatingDto.getCategory());
    taskRating.setChapter(taskRatingDto.getChapter());;
    taskRating.setStudentId(taskRatingDto.getStudentId());
    taskRating.setRating(taskRatingDto.getRating());
    taskRating.setDeleted(taskRatingDto.getDeleted());
    taskRating.setCreatedBy(taskRatingDto.getStudentId());
    taskRating.setCreatedDate(new Date());
    taskRating.setUpdatedBy(taskRatingDto.getStudentId());
    taskRating.setUpdatedDate(new Date());
    return this.taskRatingRepository.save(taskRating);
  }

  public List<TaskRating> getAllTaskRating (final Long studentId, final String category) {
    // TODO: Make category an enum
    List<TaskRating> taskRatingList = this.taskRatingRepository.findAllByStudentIdAndCategory(studentId, category);
    if (taskRatingList.size() == 0) {
      throw new IllegalArgumentException("Task rating not found for the given student id and category");
    }
    return taskRatingList;
  }

  public TaskRating updateTaskRating (final TaskRatingDto taskRatingDto) {
    final Optional<TaskRating> optionalTaskRating = this.taskRatingRepository.
        findByStudentIdAndCategoryAndChapterAndDeletedFalse(taskRatingDto.getStudentId(),
            taskRatingDto.getCategory(), taskRatingDto.getChapter());
    if (!optionalTaskRating.isPresent()) {
      throw new IllegalArgumentException("Task rating not found for the given student id, category and chapter");
    }
    final TaskRating taskRating = optionalTaskRating.get();
    taskRating.setRating(taskRatingDto.getRating());
    return this.taskRatingRepository.save(taskRating);
  }
}
