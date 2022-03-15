package com.mas.dashboard.mapper;

import com.mas.dashboard.dto.WeeklySummaryResponseDto;
import com.mas.dashboard.entity.WeeklySummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeeklySummaryResponseMapper {
    WeeklySummaryResponseMapper INSTANCE = Mappers.getMapper(WeeklySummaryResponseMapper.class);

    WeeklySummaryResponseMapper toWeeklySummaryResponseEntity (final WeeklySummaryResponseDto weeklySummaryResponseDto);

    WeeklySummaryResponseDto toWeeklySummaryResponseDto (final WeeklySummaryResponse weeklySummaryResponse);
}
