package com.mas.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
//@Builder
public class DailyWordsDto {

  private Long id;

  private String wordOne;

  private String wordOneCat;

  private String wordOneMeaning;

  private String wordTwo;

  private String wordTwoCat;

  private String wordTwoMeaning;

  private Date date;

  private Boolean deleted;
}
