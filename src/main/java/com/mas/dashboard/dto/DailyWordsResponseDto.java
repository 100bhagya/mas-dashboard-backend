package com.mas.dashboard.dto;

import lombok.Data;

@Data
public class DailyWordsResponseDto {
  private Long dailyWordsId;

  private Long studentId;

  private String responseOne;

  private String responseTwo;

  private Boolean completed;
}
