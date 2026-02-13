package com.example.Learning_Platform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "answer_options")
@Data
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}