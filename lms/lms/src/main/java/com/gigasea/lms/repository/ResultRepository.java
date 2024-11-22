package com.gigasea.lms.repository;

import com.gigasea.lms.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByStudentId(int studentId);
}

