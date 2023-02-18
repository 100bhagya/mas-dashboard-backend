package com.mas.dashboard.service;

import com.mas.dashboard.Helper.LeaderboardHelper;

import com.mas.dashboard.entity.Leaderboard;


import com.mas.dashboard.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepo;

    public void  save (MultipartFile file){
        try {
            List<Leaderboard> leaderboard = LeaderboardHelper.convertExcelToListOfLeaderboard(file.getInputStream());
            this.leaderboardRepo.saveAll(leaderboard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Leaderboard> getLeaderboardData(){
        return this.leaderboardRepo.findAll();
    }
}
