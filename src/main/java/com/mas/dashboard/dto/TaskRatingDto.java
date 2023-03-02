package com.mas.dashboard.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskRatingDto {
  private Long id;

  private String category;

  private String chapter;

  private Long studentId;

  private BigDecimal rating;

  private Boolean deleted;
}
