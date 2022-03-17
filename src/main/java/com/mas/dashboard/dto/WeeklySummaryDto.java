package com.mas.dashboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WeeklySummaryDto {
  private Long id;

  private String articleTopic;

  private String articleText;

  private Long readTime;

  private String date;

  private Boolean deleted;
}
