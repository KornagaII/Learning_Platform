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


# 📚 API Документация

---


# 📚 API Документация

---

## 1️⃣ Курсы /api/courses

| Метод  | URL                | Описание                         |
|--------|--------------------|---------------------------------|
| GET    | /api/courses       | Получить список всех курсов      |
| GET    | /api/courses/{id}  | Получить детальную информацию   |
| POST   | /api/courses       | Создать новый курс              |
| DELETE | /api/courses/{id}  | Удалить курс по ID              |

Пример создания курса (cURL):

```bash
curl -X POST http://localhost:8080/api/courses \

-H "Content-Type: application/json" \
-d '{"title": "Docker Mastery", "description": "From Zero to Hero"}'


2️⃣ Запись на курс /api/enrollments

csv


Метод,URL,Описание
POST,/api/enrollments,Записать студента на курс
Пример запроса:

JSON


{
  "studentId": 2,
  "courseId": 1
}

3️⃣ Домашние задания /api/assignments, /api/submissions

csv


Метод,URL,Описание
GET,/api/assignments/course/{id},Посмотреть задания по курсу
POST,/api/submissions,Отправить решение на проверку

4️⃣ Тесты /api/quizzes

csv


Метод,URL,Описание
GET,/api/quizzes/course/{id},Получить тест и вопросы

🧪 Тестирование

- Проект покрыт интеграционными тестами контроллеров и Spring контекста.
- Используется изолированная база данных H2.
- Запуск локально: mvn test
- ⚠️ Если появляется ошибка DataIntegrityViolationException (дублирование данных):
- Закомментируйте initData(); в DataInitializer.java
- Или почистите базу: docker-compose down -v

🏗️ Архитектура базы данных

- Проект спроектирован по 3-й нормальной форме
- Более 15 сущностей

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
