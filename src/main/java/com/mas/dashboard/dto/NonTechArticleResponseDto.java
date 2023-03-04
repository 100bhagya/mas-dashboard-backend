package com.mas.dashboard.dto;

import lombok.Data;

@Data
public class NonTechArticleResponseDto {
    private Long nonTechArticleId;

    private Long studentId;

    private String response;

    private Boolean completed;

    private Integer weekNo;

    private  Integer articleNo;
}
