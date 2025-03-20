package com.kucp1127.KidsLearningApp.ChildProfile.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRecordCopy {
    private String game;
    private String learning;
    private int coins;
}
