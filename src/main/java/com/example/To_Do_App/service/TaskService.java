package com.example.To_Do_App.service;

import com.example.To_Do_App.dto.TaskCreateDto;
import com.example.To_Do_App.dto.TaskDto;
import com.example.To_Do_App.entity.Task;
import com.example.To_Do_App.exception.UserAccessDeniedException;
import com.example.To_Do_App.mapper.TaskMapper;
import com.example.To_Do_App.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public List<TaskDto> getAllTasks(String userId){
        List<Task> tasks=taskRepository.findByUserIdOrderByPositionAsc(userId);
        List<TaskDto> taskDtos=tasks.stream().map(taskMapper::toDto).toList();
        return taskDtos;
    }

    @Transactional
    public TaskDto createTask(TaskCreateDto createDto,String userId){
        Task createdTask=taskMapper.toEntity(createDto);
        createdTask.setUserId(userId);
        createdTask.setPosition(1);
        taskRepository.save(createdTask);
        TaskDto taskDto =taskMapper.toDto(createdTask);
        return taskDto;
    }

    @Transactional
    public TaskDto updateTaskStatus(Long taskId,String userId,Boolean completed){
        Task task=taskRepository.findById(taskId).
                orElseThrow(()->new EntityNotFoundException("Task not founded"));
        if(!task.getUserId().equals(userId)) throw new UserAccessDeniedException("Access denied");
        task.setCompleted(completed);
        taskRepository.save(task);
        TaskDto taskDto=taskMapper.toDto(task);
        return taskDto;
    }

    @Transactional
    public void deleteTask(Long taskId,String userId){
        Task task=taskRepository.findById(taskId).
                orElseThrow(()->new EntityNotFoundException("Task not founded"));
        if(!task.getUserId().equals(userId)) throw new RuntimeException("Access denied");
        taskRepository.delete(task);
    }

}
