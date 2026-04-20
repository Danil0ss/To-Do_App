package com.example.To_Do_App.mapper;

import com.example.To_Do_App.dto.TaskCreateDto;
import com.example.To_Do_App.dto.TaskDto;
import com.example.To_Do_App.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto dto);
    Task toEntity(TaskCreateDto createDto);
}
