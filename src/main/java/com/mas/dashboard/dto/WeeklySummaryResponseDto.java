package com.mas.dashboard.dto;

import lombok.Data;

@Data
public class WeeklySummaryResponseDto {
    private Long weeklySummaryId;

    private Long studentId;

    private String response;

    private Boolean completed;
}
