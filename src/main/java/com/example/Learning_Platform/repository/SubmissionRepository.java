package com.example.Learning_Platform.repository;

import com.example.Learning_Platform.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}