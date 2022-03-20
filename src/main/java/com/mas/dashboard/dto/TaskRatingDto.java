package com.mas.dashboard.dto;

import lombok.Data;

@Data
public class TaskRatingDto {
  private Long id;

  private String category;

  private String chapter;

  private Long studentId;

  private Long rating;

  private Boolean deleted;
}
