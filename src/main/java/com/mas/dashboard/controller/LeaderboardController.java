package com.mas.dashboard.controller;

import com.mas.dashboard.Helper.LeaderboardHelper;

import com.mas.dashboard.entity.Leaderboard;

import com.mas.dashboard.payload.response.MessageResponse;
import com.mas.dashboard.service.LeaderboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardHelper leaderboardHelper;

    @Autowired
    private LeaderboardService leaderboardService;

    @PostMapping("/data/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if(leaderboardHelper.checkExcelFormat(file)){
            this.leaderboardService.save(file);

            return ResponseEntity.ok(new MessageResponse("The file is Uploaded and data is saved in the db successfully !"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");

    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Leaderboard> getAllLeaderboardData() {
        return this.leaderboardService.getLeaderboardData();
    }

}
