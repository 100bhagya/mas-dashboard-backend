package com.mas.dashboard.mapper;

import com.mas.dashboard.dto.WeeklySummaryDto;
import com.mas.dashboard.entity.WeeklySummary;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeeklySummaryMapper {

  WeeklySummaryMapper INSTANCE = Mappers.getMapper(WeeklySummaryMapper.class);

  WeeklySummary toWeeklySummaryEntity (final WeeklySummaryDto weeklySummaryDto);

  List<WeeklySummaryDto> toWeeklySummaryDtoList (final List<WeeklySummary> weeklySummaryList);

}
