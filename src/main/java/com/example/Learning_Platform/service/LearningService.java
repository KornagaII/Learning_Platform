package com.example.Learning_Platform.service;

import com.example.Learning_Platform.entity.Course;
import com.example.Learning_Platform.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LearningService {

    private final CourseRepository courseRepository;

    public LearningService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Метод без @Transactional — сессия закроется сразу после возврата курса
    public Course getCourseOnly(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
    }

    // Добавь эти методы в LearningService.java

    public Course getCourseWithModules(Long id) {
    // Используем наш новый метод из репозитория
        return courseRepository.findByIdWithModules(id)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
    }
}