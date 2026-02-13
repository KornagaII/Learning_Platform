package com.example.Learning_Platform.service;

import com.example.Learning_Platform.entity.*;
import com.example.Learning_Platform.entity.Module;
import com.example.Learning_Platform.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LearningService learningService;
    private final EnrollmentService enrollmentService;
    private final HomeworkService homeworkService;
    private final QuizService quizService;

    public DataInitializer(UserRepository userRepository, 
                           CourseRepository courseRepository, 
                           EnrollmentRepository enrollmentRepository,
                           LearningService learningService,
                           EnrollmentService enrollmentService,
                           HomeworkService homeworkService,
                           QuizService quizService) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.learningService = learningService;
        this.enrollmentService = enrollmentService;
        this.homeworkService = homeworkService;
        this.quizService = quizService;
    }

    @Override
    public void run(String... args) {
        // 1. Сначала вызываем метод создания данных (он в транзакции)
        initData();

        // 2. А теперь ПРОВЕРЯЕМ ленивую загрузку (уже вне транзакции метода initData)
        try {
            System.out.println("--- Проверка ленивой загрузки ---");
            // Получаем курс через сервис, где НЕТ @Transactional
            Course loadedCourse = learningService.getCourseOnly(1L); 
            System.out.println("Курс получен: " + loadedCourse.getTitle());
            
            // Здесь должна вылететь ошибка, так как сессия закрыта, а модули подгружаются лениво
            System.out.println("Узнаем количество модулей...");
            System.out.println("Количество модулей: " + loadedCourse.getModules().size()); 
        } catch (Exception e) {
            System.err.println("✅ УСПЕХ! МЫ ПОЙМАЛИ ОШИБКУ, КАК В ТЗ:");
            System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        try {
            System.out.println("--- Проверка бизнес-логики: Повторная запись на курс ---");
            // Пытаемся записать студента Анну (ID 2) на курс Hibernate (ID 1) еще раз
            enrollmentService.enrollStudent(2L, 1L);
        } catch (Exception e) {
            System.err.println("✅ СИСТЕМА ЗАЩИТЫ СРАБОТАЛА: " + e.getMessage());
        }
        try {
            System.out.println("--- Проверка работы с домашними заданиями ---");
    
            // Преподаватель создает задание к первому уроку (ID 1)
            Assignment homework = homeworkService.createAssignment(1L, "Практика JPA", "Напишите 5 сущностей");
            
            // Студент Анна (ID 2) отправляет решение
            homeworkService.submitWork(2L, homework.getId(), "Вот моя ссылка на GitHub: github.com/anna/jpa-task");
            
        } catch (Exception e) {
            System.err.println("Ошибка в HomeworkService: " + e.getMessage());
        }

        try {
            System.out.println("--- Проверка системы тестов ---");
            
            // 1. Создаем тест для первого модуля
            Quiz quiz = quizService.createQuiz(1L, "Тест по основам Hibernate");
            
            // 2. Имитируем прохождение теста студентом
            quizService.submitQuizResult(2L, quiz.getId(), 85);
            
        } catch (Exception e) {
            System.err.println("Ошибка в QuizService: " + e.getMessage());
        }    

    }

    @Transactional // Перенесли транзакцию сюда
    public void initData() {
        if (userRepository.count() > 0) return;

        System.out.println("--- Начинаем инициализацию тестовых данных ---");

        User teacher = new User();
        teacher.setName("Алексей Иванович Иванов");
        teacher.setEmail("teacher@test.com");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        Course course = new Course();
        course.setTitle("Основы Hibernate и JPA");
        course.setDescription("Курс по работе с ORM в Java");
        course.setTeacher(teacher);

        Module module1 = new Module();
        module1.setTitle("Введение");
        module1.setCourse(course);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Для чего нужен ORM?");
        lesson1.setContent("Текст первого урока...");
        lesson1.setModule(module1);

        module1.setLessons(List.of(lesson1));
        course.setModules(List.of(module1));

        courseRepository.save(course);

        User student = new User();
        student.setName("Анна");
        student.setEmail("student@test.com");
        student.setRole(Role.STUDENT);
        userRepository.save(student);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        System.out.println("--- Тестовые данные успешно созданы! ---");
    }
}