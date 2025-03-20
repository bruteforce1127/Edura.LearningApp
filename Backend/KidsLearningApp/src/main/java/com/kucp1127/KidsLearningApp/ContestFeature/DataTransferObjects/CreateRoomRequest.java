package com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
    private String creator;
    private int grade;
    private int noOfQuestions;
    private List<String> participants;
}
