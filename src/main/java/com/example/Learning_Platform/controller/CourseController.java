package com.example.Learning_Platform.controller;

import com.example.Learning_Platform.dto.CourseDto;
import com.example.Learning_Platform.entity.Course;
import com.example.Learning_Platform.entity.User;
import com.example.Learning_Platform.repository.CourseRepository;
import com.example.Learning_Platform.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository; // 1. Добавили поле репозитория

    // 2. Обновили конструктор для внедрения обеих зависимостей
    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс с ID " + id + " не найден"));
        return convertToDto(course);
    }

    // 3. Добавили метод для создания курса (POST)
    @PostMapping
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        // Берем первого попавшегося учителя из базы для теста
        User teacher = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Учитель не найден"));

        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setTeacher(teacher);

        Course saved = courseRepository.save(course);
        return convertToDto(saved);
    }

    private CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setTeacherName(course.getTeacher().getName());
        return dto;
    }
}