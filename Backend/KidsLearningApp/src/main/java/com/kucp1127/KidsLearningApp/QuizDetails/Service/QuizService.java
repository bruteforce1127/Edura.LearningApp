package com.kucp1127.KidsLearningApp.QuizDetails.Service;

import com.kucp1127.KidsLearningApp.QuizDetails.Model.Quiz;
import com.kucp1127.KidsLearningApp.QuizDetails.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(int id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Optional<List<Quiz>> getQuizByGrade(int grade) {
        return Optional.of(quizRepository.getByGrade(grade));
    }
}