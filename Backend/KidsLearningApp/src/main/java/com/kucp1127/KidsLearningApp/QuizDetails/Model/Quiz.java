package com.kucp1127.KidsLearningApp.QuizDetails.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    private int id;
    private String question;
    @ElementCollection
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "option")
    private List<String> options;
    private int correctAnswerIndex;
    private int grade;
    private String category;
}