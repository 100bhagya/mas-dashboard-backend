package com.mas.dashboard.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "daily_words_response")
public class DailyWordsResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long dailyWordsId;

  private Long studentId;

  private String responseOne;

  private String responseTwo;

  private Boolean completed;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  @CreatedDate
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @LastModifiedDate
  private Date updatedDate;

  @CreatedBy
  private Long createdBy;

  @LastModifiedBy
  private Long updatedBy;
}
