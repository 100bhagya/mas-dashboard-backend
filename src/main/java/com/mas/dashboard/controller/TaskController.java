package com.mas.dashboard.controller;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<List<DailyWordsDto>> saveDailyWords(@RequestBody List<DailyWordsDto> dailyWordsRequestList) {
        return new ResponseEntity<>(this.taskService.saveDailyWords(dailyWordsRequestList), HttpStatus.CREATED);
    }

    @GetMapping("/daily-words")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsDto> getDailyWords(@RequestParam final Long studentId,
                                                       @RequestParam final Date date) {
        return new ResponseEntity<>(this.taskService.getDailyWords(studentId, date), HttpStatus.OK);
    }

    @PostMapping("/daily-words-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsResponseDto> saveDailyWordsResponse (@RequestBody final DailyWordsResponseDto dailyWordsResponseDto) {
        return new ResponseEntity<>(this.taskService.saveDailyWordsResponse(dailyWordsResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/daily-words-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsResponseDto> getDailyWordsResponse (@RequestParam final Long studentId,
                                                                        @RequestParam final Long dailyWordsId) {
        return new ResponseEntity<>(this.taskService.getDailyWordsResponse(studentId, dailyWordsId), HttpStatus.OK);
    }

    @PutMapping("/daily-words-response")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DailyWordsResponseDto> updateDailyWordsResponse (@RequestBody final DailyWordsResponseDto dailyWordsResponseDto) {
        return new ResponseEntity<>(this.taskService.updateDailyWordsResponse(dailyWordsResponseDto), HttpStatus.OK);

    }
}
