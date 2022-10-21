package com.mas.dashboard.controller;

import com.mas.dashboard.dto.*;
import com.mas.dashboard.entity.*;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<AppUser> userAccess() {
        return appUserRepository.findAll();
    }

    @PostMapping("/daily-words")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DailyWords>> saveDailyWords(@RequestBody List<DailyWordsDto> dailyWordsRequestList) {
        return new ResponseEntity<>(this.taskService.saveDailyWords(dailyWordsRequestList), HttpStatus.CREATED);
    }

    @GetMapping("/daily-words")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWords> getDailyWords(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") final Date date) {
        return new ResponseEntity<>(this.taskService.getDailyWords(date), HttpStatus.OK);
    }


    @PostMapping("/daily-words-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsResponse> saveDailyWordsResponse (@RequestBody final DailyWordsResponseDto dailyWordsResponseDto) {
        return new ResponseEntity<>(this.taskService.saveDailyWordsResponse(dailyWordsResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/daily-words-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsResponse> getDailyWordsResponse (@RequestParam final Long studentId,
                                                                        @RequestParam final Long dailyWordsId) {
        return new ResponseEntity<>(this.taskService.getDailyWordsResponse(studentId, dailyWordsId), HttpStatus.OK);
    }

    @PutMapping("/daily-words-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsResponse> updateDailyWordsResponse (@RequestBody final DailyWordsResponseDto dailyWordsResponseDto) {
        return new ResponseEntity<>(this.taskService.updateDailyWordsResponse(dailyWordsResponseDto), HttpStatus.OK);
    }

    @GetMapping("/daily-words/check-status")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<Date, List<Boolean>>> checkDailyWordsResponseStatus (@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") final Date fromDate,
                                                                                                         @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") final Date toDate,
                                                                                                         @RequestParam final Long studentId) {
        return new ResponseEntity<>(this.taskService.checkDailyWordsResponseStatus(fromDate, toDate, studentId), HttpStatus.OK);
    }

    @GetMapping("/monthly-words")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<DailyWords>> getMonthlyWords(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") final Date startDate ,
                                                            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") final Date endDate) {
        return new ResponseEntity<>(this.taskService.getMonthlyWords(startDate, endDate), HttpStatus.OK);
    }

    @PostMapping("/weekly-summary")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WeeklySummary> saveWeeklySummary (@RequestBody final WeeklySummaryDto weeklySummaryDto) {
        return new ResponseEntity<>(this.taskService.saveWeeklySummary(weeklySummaryDto), HttpStatus.CREATED);
    }

    @GetMapping("/weekly-summary")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WeeklySummary> getWeeklySummary (@RequestParam final Integer weekNumber, @RequestParam final Integer articleNumber) {
        return new ResponseEntity<>(this.taskService.getWeeklySummary(weekNumber, articleNumber), HttpStatus.OK);
    }

    @PostMapping("/weekly-summary-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WeeklySummaryResponse> saveWeeklySummaryResponse (@RequestBody final WeeklySummaryResponseDto weeklySummaryResponseDto) {
        return new ResponseEntity<>(this.taskService.saveWeeklySummaryResponse(weeklySummaryResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/weekly-summary-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WeeklySummaryResponse> getWeeklySummaryResponse (@RequestParam final Long studentId,
                                                                     @RequestParam final Long weeklySummaryId) {
        return new ResponseEntity<>(this.taskService.getWeeklySummaryResponse(studentId, weeklySummaryId), HttpStatus.OK);
    }

    @PostMapping("task-rating")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TaskRating> createTaskRating (@RequestBody final TaskRatingDto taskRatingDto) {
        return new ResponseEntity<>(this.taskService.createTaskRating(taskRatingDto), HttpStatus.CREATED);
    }

    @GetMapping("task-rating")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TaskRating>> getTaskRating (@RequestParam final Long studentId, @RequestParam final String category) {
        return new ResponseEntity<>(this.taskService.getAllTaskRating(studentId, category), HttpStatus.OK);
    }

    @PutMapping("task-rating")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TaskRating> updateTaskRating (@RequestBody final TaskRatingDto taskRatingDto) {
        return new ResponseEntity<>(this.taskService.updateTaskRating(taskRatingDto), HttpStatus.OK);
    }
}
