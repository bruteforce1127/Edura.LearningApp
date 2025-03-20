package com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRoomRequest {
    private String roomCode;
    private String username;
}
