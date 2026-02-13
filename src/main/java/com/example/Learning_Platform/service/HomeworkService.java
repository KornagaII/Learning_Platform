package com.example.Learning_Platform.service;

import com.example.Learning_Platform.entity.*;
import com.example.Learning_Platform.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.example.Learning_Platform.repository.AssignmentRepository;

@Service
public class HomeworkService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public HomeworkService(AssignmentRepository assignmentRepository, 
                           SubmissionRepository submissionRepository, 
                           LessonRepository lessonRepository, 
                           UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    // 1. Создание задания преподавателем
    @Transactional
    public Assignment createAssignment(Long lessonId, String title, String description) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Урок не найден"));

        Assignment assignment = new Assignment();
        assignment.setLesson(lesson);
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDueDate(LocalDateTime.now().plusDays(7)); // Дедлайн через 2мнедели

        return assignmentRepository.save(assignment);
    }

    // 2. Отправка решения студентом
    @Transactional
    public Submission submitWork(Long studentId, Long assignmentId, String content) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent(content);
        submission.setSubmittedAt(LocalDateTime.now());

        System.out.println("✅ Студент " + student.getName() + " сдал работу по теме: " + assignment.getTitle());
        return submissionRepository.save(submission);
    }
}