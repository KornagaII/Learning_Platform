package com.example.Learning_Platform.repository;

import com.example.Learning_Platform.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}