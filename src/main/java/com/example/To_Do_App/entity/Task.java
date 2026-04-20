package com.example.To_Do_App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.OffsetDateTime;

@Entity
@Table(name="tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Boolean completed=false;

    @Column(nullable = false)
    private Integer position;

    private String userId;

    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate(){
        createdAt=OffsetDateTime.now();
    }
}
