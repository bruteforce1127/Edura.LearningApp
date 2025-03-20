package com.kucp1127.KidsLearningApp.PeerMatchByTeacher.Controller;


import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Leaderboard;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Score;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Service.LeaderboardService;
import com.kucp1127.KidsLearningApp.PeerMatchByTeacher.Service.PeerService;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.util.*;

@Controller
@CrossOrigin("*")
public class PeerMatchController {

    @Autowired
    private PeerService peerService;

    @Autowired
    private ChildService childService;


    @Autowired
    private LeaderboardService leaderboardService;


    @PostMapping("/peers/{grade}")
    public Mono<ResponseEntity<String>> generateRoadmap(@PathVariable int grade) {

        Map<String, Double> studentAccuracyMap = new HashMap<>();

        List<Child> children = childService.getByChildGrade(grade);
        for (Child child : children) {
            Optional<Leaderboard> leaderboard = leaderboardService.getById(child.getUsername());
            double accuracy;
            if (leaderboard.isPresent()){
                List<Score> scores = leaderboard.get().getScores();
                accuracy = 0.0;
                for (Score score : scores){
                    accuracy += score.getScore();
                }
                accuracy /= scores.size();
                accuracy *= 10;
            } else {
                accuracy = 00.0;
            }
            studentAccuracyMap.put(child.getUsername(), accuracy);
        }

        // Convert the Optional from peerService to a Mono and map it to a ResponseEntity<String>
        return Mono.justOrEmpty(peerService.getResponse(studentAccuracyMap))
                .map(response -> ResponseEntity.ok(response.toString()))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }



}
