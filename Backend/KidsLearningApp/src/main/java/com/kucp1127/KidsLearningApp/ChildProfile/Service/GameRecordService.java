package com.kucp1127.KidsLearningApp.ChildProfile.Service;


import com.kucp1127.KidsLearningApp.ChildProfile.Model.GameRecord;
import com.kucp1127.KidsLearningApp.ChildProfile.Repository.GameRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameRecordService {

    @Autowired
    private GameRecordRepository gameRecordRepository;

    public void uploadGames(GameRecord gameRecord) {
        gameRecordRepository.save(gameRecord);
    }
}
