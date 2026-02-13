package com.example.Learning_Platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content; // Текст ответа или ссылка на GitHub

    private Integer grade; // Оценка (например, от 0 до 100)

    @Column(columnDefinition = "TEXT")
    private String feedback; // Комментарий преподавателя

    private LocalDateTime submittedAt = LocalDateTime.now(); // Дата сдачи (ставится сама)

    // Связь 1: К какому заданию это решение?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    @ToString.Exclude
    private Assignment assignment;

    // Связь 2: Кто сдал?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private User student;
}