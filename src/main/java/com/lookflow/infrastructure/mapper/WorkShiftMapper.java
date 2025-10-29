package com.lookflow.infrastructure.mapper;

import com.lookflow.domain.model.valueobject.WorkShift;
import com.lookflow.infrastructure.dto.WorkShiftDto;
import com.lookflow.infrastructure.persistence.entity.WorkShiftEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WorkShiftMapper {

    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftDto toDto(WorkShift workShift);

    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShift toDomain(WorkShiftDto workShiftDto);

    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftDto entityToDto(WorkShiftEntity workShiftEntity);

    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftEntity dtoToEntity(WorkShiftDto workShiftDto);


}
