package com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListDTO {

    private String roomId;
    private LocalDateTime localDateTime;

}
