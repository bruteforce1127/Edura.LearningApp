package com.kucp1127.KidsLearningApp.ChildFriend.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildFriendDto {

    private int grade;
    private String username;
    private int coins;
    private double accuracy;
    private int noOfQuizzes;
    private int noOfGames;

}
