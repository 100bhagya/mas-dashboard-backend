package com.mas.dashboard.mapper;

import com.mas.dashboard.dto.DailyWordsDto;
import com.mas.dashboard.entity.DailyWords;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DailyWordsMapper {

  DailyWordsMapper INSTANCE = Mappers.getMapper(DailyWordsMapper.class);

  DailyWords toDailyWordsEntity (final DailyWordsDto dailyWordsRequest);

  DailyWordsDto toDailyWordsDto (final DailyWords dailyWords);

  List<DailyWordsDto> toDailyWordsDto (final List<DailyWords> dailyWordsList);

}
