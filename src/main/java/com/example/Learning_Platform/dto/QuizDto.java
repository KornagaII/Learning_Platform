package com.example.Learning_Platform.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizDto {
    private Long id;
    private String title;
    private List<String> questions; // Просто названия вопросов для краткости
}