package com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Controller;


import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Leaderboard;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Score;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.ScoreDTO;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.ScoreRequest;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/leaderboard")
@CrossOrigin("*")
public class LeaderboardController {

    @Autowired
    private  LeaderboardService leaderboardService;


    @GetMapping("/user/{username}/{category}")
    public List<ScoreDTO> getByUsernameAndCategory(@PathVariable String username,
                                                   @PathVariable String category) {
        return leaderboardService.getScoresByUsernameAndCategory(username, category);
    }


    @PostMapping("/user/{username}/score")
    public Leaderboard updateScore(@PathVariable String username,
                                   @RequestBody ScoreRequest scoreRequest) {
        return leaderboardService.updateScore(username, scoreRequest);
    }

    @GetMapping("/accuracy/{username}")
    public Map<String, Double> accuracy(@PathVariable String username) {
        Optional<Leaderboard> leaderboard = leaderboardService.getById(username);
        double accuracy;
        if(leaderboard.isPresent()){
            List<Score> scores = leaderboard.get().getScores();
            accuracy = 0.0;
            for(Score score : scores){
                accuracy += score.getScore();
            }
            accuracy /= scores.size();
            accuracy *= 10;
        } else {
            accuracy = 100.0;
        }
        return Map.of("accuracy", accuracy);
    }

    @GetMapping("/{username}/{category}")
    public ResponseEntity<?> getSubjectWiseAccuracy(@PathVariable String username ,
                                                    @PathVariable String category){

        double score = leaderboardService.getAverageScoreMessage(username,category);

        return ResponseEntity.ok(score*10);

    }


    @GetMapping("/history/{username}")
    public List<Score> getScoreHistory(@PathVariable String username){
        Optional<Leaderboard> leaderboard = leaderboardService.getById(username);
        return leaderboard.map(Leaderboard::getScores).orElse(null);
    }

}
