package com.kucp1127.KidsLearningApp.StreakFeature.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreakModel {
    @Id
    private String username;
    private int streak;
    private LocalDate localDate;
    private int longestStreak;
}
