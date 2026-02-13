package com.example.Learning_Platform.repository;

import com.example.Learning_Platform.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}