package com.kucp1127.KidsLearningApp.PracticeQuizes.Service;

import com.kucp1127.KidsLearningApp.PracticeQuizes.Model.PracticeQuestionModel;
import com.kucp1127.KidsLearningApp.PracticeQuizes.Repository.PracticeQuestionModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeQuestionModelService {

    @Autowired
    private PracticeQuestionModelRepository repository;

    public PracticeQuestionModel save(PracticeQuestionModel model) {
        return repository.save(model);
    }

    public List<PracticeQuestionModel> getByGrade(int grade) {
        return repository.findByGrade(grade);
    }

    public List<PracticeQuestionModel> getByGradeAndCategory(int grade, String category) {
        return repository.findByGradeAndCategory(grade, category);
    }

    public List<PracticeQuestionModel> getByGradeAndCategoryAndSubCategory(int grade, String category, String subCategory) {
        return repository.findByGradeAndCategoryAndSubCategory(grade, category, subCategory);
    }

    public List<PracticeQuestionModel> saveAll(List<PracticeQuestionModel> modelList) {
        return repository.saveAll(modelList);
    }

}
