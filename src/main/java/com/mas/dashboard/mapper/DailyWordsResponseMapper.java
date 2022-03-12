package com.mas.dashboard.mapper;

import com.mas.dashboard.dto.DailyWordsResponseDto;
import com.mas.dashboard.entity.DailyWordsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DailyWordsResponseMapper {
  DailyWordsResponseMapper INSTANCE = Mappers.getMapper(DailyWordsResponseMapper.class);

  DailyWordsResponse toDailyWordsResponseEntity (final DailyWordsResponseDto dailyWordsResponseDto);

  DailyWordsResponseDto toDailyWordsResponseDto (final DailyWordsResponse dailyWordsResponse);
}
