package com.kucp1127.KidsLearningApp.ChildProfile.Controller;


import com.kucp1127.KidsLearningApp.ChildProfile.Model.*;
import com.kucp1127.KidsLearningApp.ChildProfile.Service.ChildProfileService;
import com.kucp1127.KidsLearningApp.ChildProfile.Service.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/childProfile")
public class ChildProfileController {

    @Autowired
    private ChildProfileService childProfileService;

    @Autowired
    private GameRecordService gameRecordService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getById(@PathVariable String username){
        Optional<ChildProfile> childProfile = childProfileService.getById(username);
        if(childProfile.isPresent()){
            return new ResponseEntity<>(childProfile.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> postChildProfile(@RequestBody ChildProfile childProfile){
        Optional<ChildProfile> childProfile1 = childProfileService.postChildProfile(childProfile);
        if(childProfile1.isPresent()){
            return new ResponseEntity<>(childProfile1.get(),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> updateChildProfile(
            @PathVariable String username,
            @RequestBody GameRecordCopy gameRecordCopy) {

        Optional<ChildProfile> childProfileOpt = childProfileService.getById(username);
        if (childProfileOpt.isPresent()) {
            ChildProfile childProfile = childProfileOpt.get();
            List<GameRecord> gameRecordList = childProfile.getGameRecords();
            boolean recordFound = false;
            String newGameUrl = gameRecordCopy.getGame();

            for (GameRecord gameRecord : gameRecordList) {
                if (gameRecord.getGame() != null && gameRecord.getGame().equals(newGameUrl)) {
                    recordFound = true;
                    ScoreEntry scoreEntry = new ScoreEntry();
                    scoreEntry.setTimestamp(LocalDateTime.now());
                    gameRecord.getScoreHistory().add(scoreEntry);
                    gameRecord.setLearning(gameRecordCopy.getLearning());
                    // Remove the direct call to uploadGames() here.
                    break;
                }
            }

            if (!recordFound) {
                // Create a new GameRecord if one with the given gameUrl does not exist
                GameRecord gameRecord = new GameRecord();
                gameRecord.setGame(gameRecordCopy.getGame());
                gameRecord.setLearning(gameRecordCopy.getLearning());
                // Set the bidirectional association
                gameRecord.setChildProfile(childProfile);

                List<ScoreEntry> scoreEntryList = new ArrayList<>();
                ScoreEntry scoreEntry = new ScoreEntry();
                scoreEntry.setTimestamp(LocalDateTime.now());
                scoreEntryList.add(scoreEntry);
                gameRecord.setScoreHistory(scoreEntryList);

                // Add the new game record to the ChildProfile
                childProfile.getGameRecords().add(gameRecord);
            }

            int coins = childProfile.getCoins();
            coins+=gameRecordCopy.getCoins();

            childProfile.setCoins(coins);

            // Save the ChildProfile to persist changes, cascading to GameRecord
            Optional<ChildProfile> updatedChildProfile = childProfileService.postChildProfile(childProfile);
            if (updatedChildProfile.isPresent()) {
                return new ResponseEntity<>(updatedChildProfile.get(), HttpStatus.OK);
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/{username}/avatars")
    public ResponseEntity<?> addAvatar(
            @PathVariable String username,
            @RequestBody String avatar
    ) {
        Optional<ChildProfile> childProfileOpt = childProfileService.getById(username);
        if (childProfileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ChildProfile childProfile = childProfileOpt.get();
        childProfile.addAvatar(avatar);

        Optional<ChildProfile> updatedChildProfile = childProfileService.postChildProfile(childProfile);
        if (updatedChildProfile.isPresent()) {
            return new ResponseEntity<>(updatedChildProfile.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PutMapping("/addcoins/{username}")
    public ResponseEntity<?> addCoin(@PathVariable String username , @RequestBody int coins){
        Optional<ChildProfile> childProfile = childProfileService.getById(username);
        if(childProfile.isPresent()){
            ChildProfile childProfile1 = childProfile.get();
            int coin  = childProfile1.getCoins();
            coin+=coins;
            childProfile1.setCoins(coin);
            Optional<ChildProfile> updatedChildProfile = childProfileService.postChildProfile(childProfile1);
            if (updatedChildProfile.isPresent()) {
                return new ResponseEntity<>(updatedChildProfile.get(), HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PutMapping("/dailychallanges/{username}")
    public ResponseEntity<?> addDailyChallengeResult(
            @PathVariable String username ,
            @RequestBody DailyQuestionCorrect dailyQuestionCorrect
            ){

        Optional<ChildProfile> childProfile = childProfileService.getById(username);
        if(childProfile.isPresent()){
            ChildProfile childProfile1 = childProfile.get();


            List<DailyQuestionCorrect> dailyQuestionCorrects = childProfile1.getDailyQuestionCorrect();
            dailyQuestionCorrects.add(dailyQuestionCorrect);

            childProfile1.setDailyQuestionCorrect(dailyQuestionCorrects);

            Optional<ChildProfile> updatedChildProfile = childProfileService.postChildProfile(childProfile1);
            if (updatedChildProfile.isPresent()) {
                return new ResponseEntity<>(updatedChildProfile.get(), HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }


}
