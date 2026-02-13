package com.example.Learning_Platform.dto;

import lombok.Data;

@Data
public class SubmissionRequestDto {
    private Long studentId;
    private Long assignmentId;
    private String content;
}