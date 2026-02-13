# 🎓 Learning_Platform

**Learning_Platform** — это Cloud-Native backend для образовательной онлайн-платформы, охватывающий полный цикл обучения: создание курсов, запись студентов, выполнение домашних заданий и тестирование.

---

## 🚀 Ключевые возможности

### 📚 Управление контентом  

- Иерархия: Курс → Модули → Уроки  
- Каскадные связи OneToMany и ManyToOne  
- Lazy Loading для оптимальной загрузки и решения проблемы N+1

### 🎓 Процесс обучения  

- Регистрация студентов с проверкой уникальности и связью ManyToMany  
- Домашние задания: создание преподавателем, отправка студентами  
- Тестирование: модульные тесты с вопросами и вариантами ответов, автоматический подсчёт результатов

### 🛠 Техническое совершенство  

- Multi-stage Docker сборка (Maven внутри контейнера)  
- Полный REST API с использованием DTO  
- Централизованная обработка ошибок через `@ControllerAdvice`  
- Автонаполнение базы демо-данными при старте

---

## 🛠️ Технологический стек

| Технология      | Назначение                     |
|-----------------|-------------------------------|
| Java 17         | Основной язык разработки       |
| Spring Boot 3   | Фреймворк приложения           |
| Spring Data JPA | Работа с БД (Hibernate)        |
| PostgreSQL      | Реляционная база данных        |
| Docker          | Контейнеризация приложения и БД|
| Docker Compose  | Оркестрация сервисов           |
| Lombok          | Сокращение бойлерплейт-кода    |

---

## 🐳 Запуск проекта через Docker

1️⃣ В корне проекта выполните:

```bash
docker-compose up --build

- Docker скачает образ Maven, соберёт .jar файл приложения
- Создаст лёгкий образ с JRE 17 (Alpine Linux)
- Запустит PostgreSQL и Backend, свяжет их вместе

2️⃣ Откройте в браузере:
http://localhost:8080

3️⃣ Остановка проекта:

Bash


docker-compose down

# 💻 Локальная разработка

- Запуск базы данных:

Bash


docker-compose up db -d

- Запуск приложения из IDE (IntelliJ IDEA, Eclipse, VS Code):

Bash


./mvnw spring-boot:run
(Приложение подключится к базе на порту 5432)



## 📡 API Документация

Реализован полный REST API с разнообразными эндпоинтами для работы с сущностями платформы.

# 📚 API Документация

---

## 1️⃣ Курсы `/api/courses`

| Метод | URL               | Описание                      |
|-------|-------------------|-------------------------------|
| GET   | `/api/courses`     | Получить список всех курсов    |
| GET   | `/api/courses/{id}`| Получить детальную информацию о курсе |
| POST  | `/api/courses`     | Создать новый курс             |
| DELETE| `/api/courses/{id}`| Удалить курс по ID             |

**Пример создания курса (cURL):**

```bash
curl -X POST http://localhost:8080/api/courses \

-H "Content-Type: application/json" \
-d '{"title": "Docker Mastery", "description": "From Zero to Hero"}'


2️⃣ Запись на курс /api/enrollments

csv


Метод,URL,Описание
POST,/api/enrollments,Записать студента на курс
Пример запроса (тело JSON):

JSON


{
  "studentId": 2,
  "courseId": 1
}

3️⃣ Домашние задания /api/assignments, /api/submissions

csv


Метод,URL,Описание
GET,/api/assignments/course/{id},Показать задания по ID курса
POST,/api/submissions,Отправить решение на проверку

4️⃣ Тесты /api/quizzes

csv


Метод,URL,Описание
GET,/api/quizzes/course/{id},Получить тест и вопросы для курса

🧪 Тестирование

- Проект покрыт интеграционными тестами REST-контроллеров и Spring контекста.

- Тесты запускаются с использованием изолированной in-memory базы H2.

- Для запуска тестов локально выполните:

shell


mvn test

⚠️ Важно
При ошибке DataIntegrityViolationException (дублирование данных) в тестах:

- Закомментируйте вызов initData(); в методе run класса DataInitializer.java
- Или очистите базу данных командой:

shell


docker-compose down -v

🏗️ Архитектура базы данных

- Модель построена по 3-й нормальной форме
- Включает свыше 15 сущностей для полной поддержки функционала

📂 Структура проекта


src/main/java/com/example/learningplatform
├── controller        # REST контроллеры (API Layer)
├── dto               # Data Transfer Objects
├── entity            # JPA сущности (Database Layer)
├── repository        # DAO (доступ к БД)
├── service           # Бизнес-логика
├── Dockerfile        # Сборка Docker образа
├── docker-compose.yml# Оркестрация приложения и БД
└── LearningPlatformApplication.java
