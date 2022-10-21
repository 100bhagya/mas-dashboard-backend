package com.mas.dashboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WeeklySummaryDto {
  private Long id;

  private String articleTopic;

  private String articleText;

  private String author;

  private String category;

  private Long readTime;

  private Boolean deleted;

  private Integer weekNumber;

  private Integer articleNumber;
}
