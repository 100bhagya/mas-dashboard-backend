package com.mas.dashboard.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "daily_words")
//@Builder
public class DailyWords {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String wordOne;

  private String wordOneCat;

  private String wordOneMeaning;

  private String wordTwo;

  private String wordTwoCat;

  private String wordTwoMeaning;

  private Boolean deleted;

  private Date date;

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

  @PrePersist
  public void prePersist() {
    if (Objects.isNull(this.deleted)) {
      this.deleted = Boolean.FALSE;
    }
  }

}
