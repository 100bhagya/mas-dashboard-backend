package com.mas.dashboard.dto;

import lombok.Data;

@Data
public class WeeklySummaryResponseDto {

    private Long articleId;

    private String summaryResponse;

    private Boolean completed;
}
