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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public List<TaskDto> getAllTasks(String userId){
        List<Task> tasks=taskRepository.findByUserIdOrderByPositionAsc(userId);
        return tasks.stream().map(taskMapper::toDto).toList();
    }

    @Transactional
    public TaskDto createTask(TaskCreateDto createDto,String userId){
        Task createdTask=taskMapper.toEntity(createDto);
        createdTask.setUserId(userId);
        int maxPosition = taskRepository.findMaxPositionByUserId(userId).orElse(0);
        createdTask.setPosition(maxPosition + 1);
        taskRepository.save(createdTask);
        return taskMapper.toDto(createdTask);
    }

    @Transactional
    public TaskDto updateTaskStatus(Long taskId,String userId,Boolean completed){
        Task task=taskRepository.findById(taskId).
                orElseThrow(()->new EntityNotFoundException("Task not found"));
        if(!task.getUserId().equals(userId)) throw new UserAccessDeniedException("Access denied");
        task.setCompleted(completed);
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Transactional
    public void deleteTask(Long taskId,String userId){
        Task task=taskRepository.findById(taskId).
                orElseThrow(()->new EntityNotFoundException("Task not found"));
        if(!task.getUserId().equals(userId)) throw new UserAccessDeniedException("Access denied");
        taskRepository.delete(task);
        taskRepository.updateTaskPosition(userId,task.getPosition());
    }

    public TaskDto changeTaskPosition(Long taskId,String userId,Integer newPosition){
        Task task=taskRepository.findById(taskId).
                orElseThrow(()-> new EntityNotFoundException("Task not found"));
        if(!task.getUserId().equals(userId)) throw new UserAccessDeniedException("Access denied");
        Integer oldPosition= task.getPosition();
        if(newPosition.equals(oldPosition)) return taskMapper.toDto(task);
        if(newPosition<oldPosition) taskRepository.incrementPositionsBetween(task.getUserId(),
                newPosition, task.getPosition());
        else taskRepository.decrementPositionsBetween(task.getUserId(),
                newPosition, task.getPosition());
        task.setPosition(newPosition);
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }
}
