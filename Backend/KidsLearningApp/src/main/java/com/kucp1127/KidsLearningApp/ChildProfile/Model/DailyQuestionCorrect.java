package com.kucp1127.KidsLearningApp.ChildProfile.Model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyQuestionCorrect {

    private int count;
    private LocalDateTime timestamp;

}
