package com.mas.dashboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WeeklySummaryDto {

  private Long id;

  private String articleTopic;

  private String articleText;

  private Long readTime;

  private Date date;

  private Boolean deleted;
}
