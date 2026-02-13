package com.example.Learning_Platform.repository;

import com.example.Learning_Platform.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Способ 1: Явный JOIN FETCH (через запрос JPQL)
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.modules WHERE c.id = :id")
    Optional<Course> findByIdWithModules(@Param("id") Long id);

    // Способ 2: EntityGraph (более современный способ Spring Data)
    @EntityGraph(attributePaths = {"modules"})
    Optional<Course> findWithModulesGraphById(Long id);
}