package com.gigasea.lms.service;

import com.gigasea.lms.model.Result;

import java.util.List;

public interface ResultService {
    /**
     * Fetch all results from the database.
     *
     * @return List of all results
     */
    List<Result> getAllResults();

    /**
     * Fetch results for a specific student by their ID.
     *
     * @param studentId ID of the student
     * @return List of results for the student
     */
    List<Result> getResultsByStudentId(int studentId);

    /**
     * Save a new result to the database.
     *
     * @param result Result object to save
     */
    void saveResult(Result result);
}


