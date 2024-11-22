package com.gigasea.lms.controller;

import com.gigasea.lms.model.Question;
import com.gigasea.lms.model.Result;
import com.gigasea.lms.service.QuestionService;
import com.gigasea.lms.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@SessionAttributes("selectedOptions")
public class MCQTestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResultService resultService;

    // Pre-test page to show the timer
    @GetMapping("/pre-test")
    public String showPreTestPage() {
        return "preTest"; // Thymeleaf template for the pre-test timer
    }

    @GetMapping("/take-mcq-test")
    public String getMcqTest(Model model, @RequestParam(defaultValue = "0") int q, @RequestParam Map<String, String> params) {
        List<Question> questions = questionService.getAllQuestions();
        if (questions.isEmpty()) {
            model.addAttribute("error", "No questions available.");
            return "error";
        }
        model.addAttribute("question", questions.get(q));
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("currentQuestion", q);

        Map<Integer, String> selectedOptions = (Map<Integer, String>) model.getAttribute("selectedOptions");
        if (selectedOptions == null) {
            selectedOptions = new HashMap<>();
        }
        if (params.containsKey("question" + questions.get(q).getId())) {
            selectedOptions.put(questions.get(q).getId(), params.get("question" + questions.get(q).getId()));
        }
        model.addAttribute("selectedOptions", selectedOptions);

        return "takeAssessment"; // Thymeleaf template for questions
    }

    @PostMapping("/submit-mcq-test")
    public String submitMcqTest(@RequestParam Map<String, String> params, Model model, SessionStatus status) {
        Map<Integer, String> selectedOptions = (Map<Integer, String>) model.getAttribute("selectedOptions");
        List<Question> questions = questionService.getAllQuestions();
        if (questions.isEmpty()) {
            model.addAttribute("error", "No questions available for submission.");
            return "error";
        }

        int studentId = 1;
        int score = 0;

        for (Question question : questions) {
            String selectedOption = selectedOptions.get(question.getId());
            boolean isCorrect = selectedOption != null && question.getCorrectOption().equals(selectedOption);
            if (isCorrect) {
                score++;
            }

            Result result = new Result();
            result.setStudentId(studentId);
            result.setQuestionId(question.getId());
            result.setSelectedOption(selectedOption != null ? selectedOption : "");
            result.setCorrect(isCorrect);

            resultService.saveResult(result);
        }

        status.setComplete();
        model.addAttribute("score", score);
        model.addAttribute("totalQuestions", questions.size());
        return "result";
    }
}


