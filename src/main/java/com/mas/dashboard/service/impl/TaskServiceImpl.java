package com.mas.dashboard.service.impl;

import com.mas.dashboard.dto.*;
import com.mas.dashboard.entity.*;
import com.mas.dashboard.repository.*;
import com.mas.dashboard.security.services.AppUserDetailsImpl;
import com.mas.dashboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private DailyWordRepository dailyWordRepository;

  @Autowired
  private DailyWordsResponseRepository dailyWordsResponseRepository;

  @Autowired
  private WeeklySummaryRepository weeklySummaryRepository;

  @Autowired
  private WeeklySummaryResponseRepository weeklySummaryResponseRepository;

  @Autowired
  private NonTechArticleResponseRepository nonTechArticleResponseRepository;

  @Autowired
  private TaskRatingRepository taskRatingRepository;

  // To get user id of logged-in user

  public AppUserDetailsImpl getLoggedInUser() {
    SecurityContext context = SecurityContextHolder.getContext();

    if (context == null) {
      return null;
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return null;
    }
    AppUserDetailsImpl loggedInUser = (AppUserDetailsImpl)auth.getPrincipal();
    return loggedInUser;
  }

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

      AppUserDetailsImpl loggedInUser = getLoggedInUser();

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
      dailyWords.setCreatedBy(loggedInUser.getId());
      dailyWords.setCreatedDate(new Date());
      dailyWords.setUpdatedBy(loggedInUser.getId());
      dailyWords.setUpdatedDate(new Date());
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
    if (!dailyWordsResponseDto.getResponseOne().isEmpty() && !dailyWordsResponseDto.getResponseTwo().isEmpty()) {
      dailyWordsResponse.setCompleted(Boolean.TRUE);
    } else {
      dailyWordsResponse.setCompleted(Boolean.FALSE);
    }
    return this.dailyWordsResponseRepository.save(dailyWordsResponse);
  }

  public DailyWordsResponse getDailyWordsResponse (final Long dailyWordsId) {
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(loggedInUser.getId(), dailyWordsId);
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

    if(dailyWordsResponseDto.getResponseOne().isEmpty() && dailyWordsResponseDto.getResponseTwo().isEmpty()){
      this.dailyWordsResponseRepository.deleteById(dailyWordsResponse.getId());
      return null;
    }else{
      dailyWordsResponse.setResponseOne(dailyWordsResponseDto.getResponseOne());
      dailyWordsResponse.setResponseTwo(dailyWordsResponseDto.getResponseTwo());

      final Optional<DailyWordsResponse> optionalUpdatedDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(dailyWordsResponseDto.getStudentId(),
              dailyWordsResponseDto.getDailyWordsId());
      if(!optionalUpdatedDailyWordsResponse.get().getResponseOne().isEmpty() && !optionalUpdatedDailyWordsResponse.get().getResponseTwo().isEmpty() ){
        dailyWordsResponse.setCompleted(Boolean.TRUE);
      }else{
        dailyWordsResponse.setCompleted(Boolean.FALSE);
      }
      return this.dailyWordsResponseRepository.save(dailyWordsResponse);
    }
  }

  //    Date -> {partialComplete, completed}
  //    {false, false} -> No Response, {true, false} -> One Response only, {true, true} -> Both response
//  public Map<Date, List<Boolean>> checkDailyWordsResponseStatus (final Date fromDate, final Date toDate) {
//    final List<DailyWords> DailyWordTuples = this.dailyWordRepository.findByDateBetween(fromDate, toDate);
//    AppUserDetailsImpl loggedInUser = getLoggedInUser();
//    Map<Date, List<Boolean>> dateCompletedStatusMap = new HashMap<>();
//    DailyWordTuples.forEach(tuple -> {
//      final Optional<DailyWordsResponse> optionalDailyWordsResponse = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsId(loggedInUser.getId(), tuple.getId());
//      if(optionalDailyWordsResponse.isPresent()){
//        List<Boolean> al = new ArrayList<>();
//        al.add(true);
//        al.add(optionalDailyWordsResponse.get().getCompleted());
//        Date newDate = tuple.getDate();
//        Calendar c = Calendar.getInstance();
//        dateCompletedStatusMap.put(newDate, al);
//      }else{
//        List<Boolean> al = new ArrayList<>();
//        al.add(false);
//        al.add(false);
//        Date newDate = tuple.getDate();
//        dateCompletedStatusMap.put(newDate, al);
//      }
//
//    });
//    return dateCompletedStatusMap;
//  }

  public Map<Date, List<Boolean>> checkDailyWordsResponseStatus(Date fromDate, Date toDate) {
    List<DailyWords> dailyWordTuples = this.dailyWordRepository.findByDateBetween(fromDate, toDate);
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    Map<Date, List<Boolean>> dateCompletedStatusMap = new HashMap<>(dailyWordTuples.size());
    Calendar c = Calendar.getInstance();

    // Retrieve all DailyWordsResponse entities for the given loggedInUser
    List<Long> dailyWordsIds = dailyWordTuples.stream()
            .map(DailyWords::getId)
            .collect(Collectors.toList());
    List<DailyWordsResponse> dailyWordsResponses = this.dailyWordsResponseRepository.findByStudentIdAndDailyWordsIdIn(loggedInUser.getId(), dailyWordsIds);
//    System.out.println(dailyWordsResponses);

    // Creating a map of DailyWords ID to DailyWordsResponse
    Map<Long, DailyWordsResponse> responseMap = dailyWordsResponses.stream()
            .collect(Collectors.toMap(DailyWordsResponse::getDailyWordsId, Function.identity()));

    // Create a SimpleDateFormat object to format the date
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    outputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

    for (DailyWords tuple : dailyWordTuples) {
      List<Boolean> al = new ArrayList<>(2);

      DailyWordsResponse dailyWordsResponse = responseMap.get(tuple.getId());
      if (dailyWordsResponse != null) {
        al.add(true);
        al.add(dailyWordsResponse.getCompleted());
      } else {
        al.add(false);
        al.add(false);
      }

      c.setTime(tuple.getDate());
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      c.set(Calendar.MILLISECOND, 0);
      Date formattedDate = c.getTime();
      String formattedDateString = outputDateFormat.format(formattedDate);

      try {
        Date convertedDate = outputDateFormat.parse(formattedDateString);
        dateCompletedStatusMap.put(convertedDate, al);
      } catch (ParseException e) {
        // Handle the exception or rethrow it as needed
        e.printStackTrace();
      }
    }

    return dateCompletedStatusMap;
  }
  public List<DailyWords> getMonthlyWords (final Date startDate, final Date endDate) {
    final List<DailyWords> DailyWordTuples = this.dailyWordRepository.findByDateBetween(startDate, endDate);
    if (DailyWordTuples.isEmpty()) {
      throw new IllegalArgumentException("Daily words for the given month not found");
    }
    return DailyWordTuples;
  }

  public WeeklySummary saveWeeklySummary (final WeeklySummaryDto weeklySummaryDto) {
    final Optional<WeeklySummary> optionalWeeklySummary = this.weeklySummaryRepository.
            findByWeekNumberAndArticleNumberAndDeletedFalse(weeklySummaryDto.getWeekNumber(), weeklySummaryDto.getArticleNumber());
    if (optionalWeeklySummary.isPresent() ) {
      throw new IllegalArgumentException("Weekly summary already exists for the given week");
    }

    AppUserDetailsImpl loggedInUser = getLoggedInUser();

    final WeeklySummary weeklySummary = new WeeklySummary();
    weeklySummary.setArticleTopic(weeklySummaryDto.getArticleTopic());
    weeklySummary.setArticleText(weeklySummaryDto.getArticleText());
    weeklySummary.setAuthor(weeklySummaryDto.getAuthor());
    weeklySummary.setCategory(weeklySummaryDto.getCategory());
    weeklySummary.setReadTime(weeklySummaryDto.getReadTime());
    weeklySummary.setWeekNumber(weeklySummaryDto.getWeekNumber());
    weeklySummary.setArticleNumber(weeklySummaryDto.getArticleNumber());
    weeklySummary.setCreatedBy(loggedInUser.getId());
    weeklySummary.setCreatedDate(new Date());
    weeklySummary.setUpdatedBy(loggedInUser.getId());
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

  public WeeklySummaryResponse saveWeeklySummaryResponse (final WeeklySummaryResponseDto weeklySummaryResponseDto) {
    final Optional<WeeklySummaryResponse> optionalWeeklySummaryResponse = this.weeklySummaryResponseRepository.
            findByStudentIdAndWeeklySummaryId(weeklySummaryResponseDto.getStudentId(), weeklySummaryResponseDto.getWeeklySummaryId());
    if (optionalWeeklySummaryResponse.isPresent()) {
      throw new IllegalArgumentException("Weekly summary response already exists");
    }
    final WeeklySummaryResponse weeklySummaryResponse = new WeeklySummaryResponse();
    weeklySummaryResponse.setWeeklySummaryId(weeklySummaryResponseDto.getWeeklySummaryId());
    weeklySummaryResponse.setStudentId(weeklySummaryResponseDto.getStudentId());
    weeklySummaryResponse.setResponse(weeklySummaryResponseDto.getResponse());
    weeklySummaryResponse.setCreatedBy(weeklySummaryResponseDto.getStudentId());
    weeklySummaryResponse.setCreatedDate(new Date());
    weeklySummaryResponse.setUpdatedBy(weeklySummaryResponseDto.getStudentId());
    weeklySummaryResponse.setUpdatedDate(new Date());
    if (weeklySummaryResponseDto.getResponse().isEmpty()) {
      weeklySummaryResponse.setCompleted(Boolean.FALSE);
    } else {
      weeklySummaryResponse.setCompleted(Boolean.TRUE);
    }
    return this.weeklySummaryResponseRepository.save(weeklySummaryResponse);
  }

  public WeeklySummaryResponse getWeeklySummaryResponse (final Long weeklySummaryId) {
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    final Optional<WeeklySummaryResponse> optionalWeeklySummaryResponse = this.weeklySummaryResponseRepository.findByStudentIdAndWeeklySummaryId(loggedInUser.getId(), weeklySummaryId);
    if (!optionalWeeklySummaryResponse.isPresent()) {
      return null;
    }
    return optionalWeeklySummaryResponse.get();
  }

//  returns Map as [weekNo -> {article1CompleteStatus, article2CompleteStatus}]
  public Map<Integer,boolean[]> weeklySummaryResponseStatus() {
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    List<WeeklySummaryResponse> weeklySummaryResponseList = this.weeklySummaryResponseRepository.findByStudentIdAndCompleted(loggedInUser.getId(), true);
//    System.out.println("weekly summary list");
//    System.out.println(weeklySummaryResponseList);
    Map<Integer, boolean[]> mapOfWeekNoArticleStatus = new HashMap<>();
    weeklySummaryResponseList.forEach( weeklySummaryResponse -> {
      Optional<WeeklySummary> weeklySummary = this.weeklySummaryRepository.findById(weeklySummaryResponse.getWeeklySummaryId());
//      weeklySummaryResponse.getWeeklySummaryId()
//      System.out.println(weeklySummaryResponse.getWeeklySummaryId());
//      System.out.println(weeklySummary);
      int weekNo = weeklySummary.get().getWeekNumber();
      int articleNo = weeklySummary.get().getArticleNumber();
      System.out.println(weekNo);
      System.out.println(articleNo);

      boolean[] arr = mapOfWeekNoArticleStatus.getOrDefault(weekNo, new boolean[2]);
      arr[articleNo - 1] = true;
      mapOfWeekNoArticleStatus.put(weekNo, arr);
    });
    return mapOfWeekNoArticleStatus;
  }


  public WeeklySummaryResponse updateWeeklySummaryResponse (final WeeklySummaryResponseDto weeklySummaryResponseDto) {
    final Optional<WeeklySummaryResponse> optionalWeeklySummaryResponse = this.weeklySummaryResponseRepository.findByStudentIdAndWeeklySummaryId(weeklySummaryResponseDto.getStudentId(),
            weeklySummaryResponseDto.getWeeklySummaryId());
    if (!optionalWeeklySummaryResponse.isPresent()) {
      throw new IllegalArgumentException("Weekly summary response not found for the given student and word id");
    }
    final WeeklySummaryResponse weeklySummaryResponse = optionalWeeklySummaryResponse.get();
    weeklySummaryResponse.setResponse(weeklySummaryResponseDto.getResponse());
    weeklySummaryResponse.setUpdatedDate(new Date());
    if (!weeklySummaryResponseDto.getResponse().isEmpty()) {
      weeklySummaryResponse.setCompleted(Boolean.TRUE);
    }else{
      weeklySummaryResponse.setCompleted(Boolean.FALSE);
    }
    return this.weeklySummaryResponseRepository.save(weeklySummaryResponse);
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

  public List<TaskRating> getAllTaskRating (final String category) {
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    // TODO: Make category an enum
    List<TaskRating> taskRatingList = this.taskRatingRepository.findAllByStudentIdAndCategory(loggedInUser.getId(), category);
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

  public NonTechArticleResponse saveNonTechArticleResponse (final NonTechArticleResponseDto nonTechArticleResponseDto) {
    final Optional<NonTechArticleResponse> optionalNonTechArticleResponse = this.nonTechArticleResponseRepository.findByStudentIdAndNonTechArticleId(nonTechArticleResponseDto.getStudentId(), nonTechArticleResponseDto.getNonTechArticleId());
    if (optionalNonTechArticleResponse.isPresent()) {
      throw new IllegalArgumentException("Weekly summary response already exists");
    }
    final NonTechArticleResponse nonTechArticleResponse = new NonTechArticleResponse();
    nonTechArticleResponse.setNonTechArticleId(nonTechArticleResponseDto.getNonTechArticleId());
    nonTechArticleResponse.setStudentId(nonTechArticleResponseDto.getStudentId());
    nonTechArticleResponse.setResponse(nonTechArticleResponseDto.getResponse());
    nonTechArticleResponse.setArticleNo(nonTechArticleResponseDto.getArticleNo());
    nonTechArticleResponse.setWeekNo(nonTechArticleResponseDto.getWeekNo());
    nonTechArticleResponse.setCreatedBy(nonTechArticleResponseDto.getStudentId());
    nonTechArticleResponse.setCreatedDate(new Date());
    nonTechArticleResponse.setUpdatedBy(nonTechArticleResponseDto.getStudentId());
    nonTechArticleResponse.setUpdatedDate(new Date());
    if (nonTechArticleResponseDto.getResponse().isEmpty()) {
      nonTechArticleResponse.setCompleted(Boolean.FALSE);
    } else {
      nonTechArticleResponse.setCompleted(Boolean.TRUE);
    }
    return this.nonTechArticleResponseRepository.save(nonTechArticleResponse);
  }

  public NonTechArticleResponse getNonTechArticleResponse (final Long nonTechArticleId) {
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    final Optional<NonTechArticleResponse> optionalNonTechArticleResponse = this.nonTechArticleResponseRepository.findByStudentIdAndNonTechArticleId(loggedInUser.getId(), nonTechArticleId);
    if (!optionalNonTechArticleResponse.isPresent()) {
      return null;
    }
    return optionalNonTechArticleResponse.get();
  }

  //  returns Map as [weekNo -> {article1CompleteStatus, article2CompleteStatus}]
  public Map<Integer,boolean[]> nonTechArticleResponseStatus() {
    AppUserDetailsImpl loggedInUser = getLoggedInUser();
    List<NonTechArticleResponse> nonTechArticleResponseList = this.nonTechArticleResponseRepository.findByStudentIdAndCompleted(loggedInUser.getId(), true);
//    System.out.println(nonTechArticleResponseList);
    Map<Integer, boolean[]> mapOfWeekNoArticleStatus = new HashMap<>();
    nonTechArticleResponseList.forEach( nonTechArticleResponse -> {
//      System.out.println(nonTechArticleResponse);


      int articleNo = nonTechArticleResponse.getArticleNo();
      int weekNo= nonTechArticleResponse.getWeekNo();
      boolean[] arr = mapOfWeekNoArticleStatus.getOrDefault(weekNo, new boolean[2]);
      arr[articleNo - 1] = true;
      mapOfWeekNoArticleStatus.put(weekNo, arr);
    });
    return mapOfWeekNoArticleStatus;
  }


  public NonTechArticleResponse updateNonTechArticleResponse (final NonTechArticleResponseDto nonTechArticleResponseDto) {
    final Optional<NonTechArticleResponse> optionalNonTechArticleResponse = this.nonTechArticleResponseRepository.findByStudentIdAndNonTechArticleId(nonTechArticleResponseDto.getStudentId(),
            nonTechArticleResponseDto.getNonTechArticleId());
    if (!optionalNonTechArticleResponse.isPresent()) {
      throw new IllegalArgumentException("Weekly summary response not found for the given student and word id");
    }
    final NonTechArticleResponse nonTechArticleResponse = optionalNonTechArticleResponse.get();
    nonTechArticleResponse.setResponse(nonTechArticleResponseDto.getResponse());
    nonTechArticleResponse.setUpdatedDate(new Date());
    if (!nonTechArticleResponseDto.getResponse().isEmpty()) {
      nonTechArticleResponse.setCompleted(Boolean.TRUE);
    }else{
      nonTechArticleResponse.setCompleted(Boolean.FALSE);
    }
    return this.nonTechArticleResponseRepository.save(nonTechArticleResponse);
  }

}