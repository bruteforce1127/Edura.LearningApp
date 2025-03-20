package com.kucp1127.KidsLearningApp.PracticeQuizes.Repository;

import com.kucp1127.KidsLearningApp.PracticeQuizes.Model.PracticeQuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeQuestionModelRepository extends JpaRepository<PracticeQuestionModel, Integer> {

    // Find all questions for a given grade
    List<PracticeQuestionModel> findByGrade(int grade);

    // Find all questions for a given grade and category
    List<PracticeQuestionModel> findByGradeAndCategory(int grade, String category);

    // Find all questions for a given grade, category, and subCategory
    List<PracticeQuestionModel> findByGradeAndCategoryAndSubCategory(int grade, String category, String subCategory);
}
