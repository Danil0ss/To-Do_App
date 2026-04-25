package com.example.To_Do_App.controller;

import com.example.To_Do_App.dto.TaskCreateDto;
import com.example.To_Do_App.dto.TaskDto;
import com.example.To_Do_App.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@AuthenticationPrincipal Jwt jwt){
        String userId= jwt.getSubject();
        return ResponseEntity.ok(taskService.getAllTasks(userId));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskCreateDto createDto,
                                              @AuthenticationPrincipal Jwt jwt){
        String userId= jwt.getSubject();
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.
                createTask(createDto, userId));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long taskId,
                                                    @RequestParam Boolean completed,
                                                    @AuthenticationPrincipal Jwt jwt){
        String userId= jwt.getSubject();
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId,userId,completed));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId,
                                           @AuthenticationPrincipal Jwt jwt){
        String userId= jwt.getSubject();
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/position")
    public ResponseEntity<TaskDto> updateTaskPosition(@PathVariable Long taskId,
                                                      @RequestParam Integer newPosition,
                                                      @AuthenticationPrincipal Jwt jwt){
        String userId= jwt.getSubject();
        return  ResponseEntity.ok(taskService.changeTaskPosition(taskId,userId,newPosition));
    }
}
