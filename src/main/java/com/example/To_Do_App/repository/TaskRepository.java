package com.example.To_Do_App.repository;

import com.example.To_Do_App.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByUserIdOrderByPositionAsc(String userId);
}
