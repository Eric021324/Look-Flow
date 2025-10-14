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

    @Mapping(target = "id", source = "id")
    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftDto toDto(WorkShift workShift);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToWorkShiftId")
    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = "uuidToEmployeeId")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShift toDomain(WorkShiftDto workShiftDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftDto entityToDto(WorkShiftEntity workShiftEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    WorkShiftEntity dtoToEntity(WorkShiftDto workShiftDto);

    @Named("uuidToWorkShiftId")
    default WorkShift uuidToWorkShiftId(UUID uuid) {
        // This would need to be implemented based on your WorkShift constructor
        // For now, returning null as it should be handled by the domain
        return null;
    }

    @Named("uuidToEmployeeId")
    default UUID uuidToEmployeeId(UUID uuid) {
        return uuid;
    }
}
