package com.example.Learning_Platform.service;

import com.example.Learning_Platform.entity.*;
import com.example.Learning_Platform.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;

    public QuizService(QuizRepository quizRepository, 
                       QuizSubmissionRepository quizSubmissionRepository, 
                       UserRepository userRepository, 
                       ModuleRepository moduleRepository) {
        this.quizRepository = quizRepository;
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
    }

    // Создание теста преподавателем
    @Transactional
    public Quiz createQuiz(Long moduleId, String title) {
        com.example.Learning_Platform.entity.Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Модуль не найден"));

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setModule(module);
        return quizRepository.save(quiz);
    }

    // Сохранение результата теста студентом
    @Transactional
    public void submitQuizResult(Long studentId, Long quizId, int score) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Тест не найден"));

        QuizSubmission result = new QuizSubmission();
        result.setStudent(student);
        result.setQuiz(quiz);
        result.setScore(score);
        
        quizSubmissionRepository.save(result);
        System.out.println("✅ Результат теста '" + quiz.getTitle() + "' для студента " + student.getName() + ": " + score + "%");
    }
}