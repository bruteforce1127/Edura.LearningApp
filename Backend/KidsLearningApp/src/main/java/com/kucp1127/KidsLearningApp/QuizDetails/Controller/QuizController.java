package com.kucp1127.KidsLearningApp.QuizDetails.Controller;

import com.kucp1127.KidsLearningApp.QuizDetails.Model.Quiz;
import com.kucp1127.KidsLearningApp.QuizDetails.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;


    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> createQuiz(@RequestBody List<Quiz> quiz) {

        List<Quiz> quizList= new ArrayList<>();

        for(Quiz quiz1 : quiz){
            quizList.add(quizService.createQuiz(quiz1));
        }

        return ResponseEntity.ok(quizList);
    }

    @GetMapping("/grade/{grade}")
    public ResponseEntity<?> getQuizByGrade(@PathVariable int grade){
        Optional<List<Quiz>> quizList = quizService.getQuizByGrade(grade);
        if(quizList.isPresent()){
            return new ResponseEntity<>(quizList.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}