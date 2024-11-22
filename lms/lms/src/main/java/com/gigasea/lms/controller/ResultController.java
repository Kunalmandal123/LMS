package com.gigasea.lms.controller;

import com.gigasea.lms.model.Result;
import com.gigasea.lms.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ResultController {

    @Autowired
    private ResultService resultService;

    /**
     * Displays the list of all results for students.
     */
    @GetMapping("/results")
    public String viewResults(Model model) {
        // Fetch all results from the database
        List<Result> results = resultService.getAllResults();

        // Pass the results to the view
        model.addAttribute("results", results);
        return "viewResults";
    }

    /**
     * Displays individual student result after submitting the test.
     */
    @GetMapping("/view-result")
    public String viewIndividualResult(Model model) {
        // Example: Fetch specific student result based on an ID (replace with actual logic)
        int studentId = 1; // Replace this with dynamic fetching (e.g., session or user context)
        List<Result> studentResults = resultService.getResultsByStudentId(studentId);

        // Calculate total score and percentage
        int totalQuestions = studentResults.size();
        long correctAnswers = studentResults.stream().filter(Result::isCorrect).count();
        int percentage = (int) ((correctAnswers * 100) / totalQuestions);

        // Add data to the model
        model.addAttribute("studentResults", studentResults);
        model.addAttribute("score", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("percentage", percentage);

        return "result"; // Maps to result.html
    }
}

