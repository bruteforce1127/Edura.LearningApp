package com.kucp1127.KidsLearningApp.PracticeQuizes.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeQuestionModel {
    @Id
    private int id;
    private String question;
    @ElementCollection
    @CollectionTable(name = "practice_options", joinColumns = @JoinColumn(name = "practice_id"))
    @Column(name = "option")
    private List<String> options;
    private int correctAnswerIndex;
    private int grade;
    private String category;
    private String subCategory;
}
