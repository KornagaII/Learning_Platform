package com.example.Learning_Platform.controller;

import com.example.Learning_Platform.dto.SubmissionRequestDto;
import com.example.Learning_Platform.entity.Submission;
import com.example.Learning_Platform.service.HomeworkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
public class HomeworkController {

    private final HomeworkService homeworkService;

    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @PostMapping
    public String submit(@RequestBody SubmissionRequestDto dto) {
        homeworkService.submitWork(dto.getStudentId(), dto.getAssignmentId(), dto.getContent());
        return "Задание успешно отправлено на проверку";
    }
}