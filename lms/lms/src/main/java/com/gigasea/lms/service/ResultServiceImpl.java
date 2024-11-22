package com.gigasea.lms.service;

import com.gigasea.lms.model.Result;
import com.gigasea.lms.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepository;

    /**
     * Fetch all results from the database.
     *
     * @return List of all results
     */
    @Override
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    /**
     * Fetch results for a specific student by their ID.
     *
     * @param studentId ID of the student
     * @return List of results for the student
     */
    @Override
    public List<Result> getResultsByStudentId(int studentId) {
        return resultRepository.findByStudentId(studentId);
    }

    /**
     * Save a new result to the database.
     *
     * @param result Result object to save
     */
    @Override
    public void saveResult(Result result) {
        resultRepository.save(result);
    }
}
