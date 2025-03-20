package com.kucp1127.KidsLearningApp.QuizDetails.Repository;

import com.kucp1127.KidsLearningApp.QuizDetails.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> getByGrade(int grade);
    // Additional custom queries can be defined here if needed
}
