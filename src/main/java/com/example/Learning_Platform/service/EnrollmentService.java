package com.example.Learning_Platform.service;

import com.example.Learning_Platform.entity.Course;
import com.example.Learning_Platform.entity.Enrollment;
import com.example.Learning_Platform.entity.User;
import com.example.Learning_Platform.repository.CourseRepository;
import com.example.Learning_Platform.repository.EnrollmentRepository;
import com.example.Learning_Platform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, 
                             UserRepository userRepository, 
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void enrollStudent(Long studentId, Long courseId) {
        // 1. Ищем студента
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));

        // 2. Ищем курс
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));

        // 3. Проверяем, не записан ли уже (используем метод из репозитория, который мы создали ранее)
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Студент уже существует");
        }

        // 4. Создаем запись
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentRepository.save(enrollment);
        System.out.println("✅ Студент " + student.getName() + " успешно записан на курс: " + course.getTitle());
    }
}