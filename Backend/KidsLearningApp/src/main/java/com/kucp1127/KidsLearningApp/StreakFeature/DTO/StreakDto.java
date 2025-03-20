package com.kucp1127.KidsLearningApp.StreakFeature.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreakDto {
    private int streak;
    private int longestStreak;
}
