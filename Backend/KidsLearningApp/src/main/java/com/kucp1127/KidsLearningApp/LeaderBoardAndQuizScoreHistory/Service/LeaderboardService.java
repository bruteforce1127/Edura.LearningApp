package com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Service;

import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Leaderboard;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Score;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.ScoreDTO;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.ScoreRequest;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;


    public List<ScoreDTO> getScoresByUsernameAndCategory(String username, String category) {
        return leaderboardRepository.findById(username)
                .map(leaderboard -> leaderboard.getScores().stream()
                        .filter(score -> score.getCategory().equalsIgnoreCase(category))
                        .map(score -> new ScoreDTO(score.getScore(), score.getTimeDate()))
                        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            Collections.reverse(list);
                            return list;
                        })))
                .orElse(List.of());
    }



    public double getAverageScoreMessage(String username, String category) {
        return leaderboardRepository.findById(username)
                .map(leaderboard -> {
                    List<Score> userScores = leaderboard.getScores().stream()
                            .filter(score -> score.getCategory().equalsIgnoreCase(category))
                            .collect(Collectors.toList());

                    if (userScores.isEmpty()) {
                        return 0.0;
                    }

                    double sum = userScores.stream().mapToDouble(Score::getScore).sum();
                    double avg = sum / userScores.size();
                    return Math.round(avg * 100.0) / 100.0;
                })
                .orElse(0.0);
    }


    public Leaderboard updateScore(String username, ScoreRequest scoreRequest) {
        Leaderboard leaderboard = leaderboardRepository.findById(username)
                .orElseGet(() -> {
                    Leaderboard newLeaderboard = new Leaderboard();
                    newLeaderboard.setUsername(username);
                    newLeaderboard.setScores(new ArrayList<>());
                    return newLeaderboard;
                });

        Score newScore = new Score();
        newScore.setCategory(scoreRequest.getCategory());
        newScore.setScore(scoreRequest.getScore()/10);
        newScore.setTimeDate(LocalDateTime.now());

        leaderboard.getScores().add(newScore);
        return leaderboardRepository.save(leaderboard);
    }

    public Leaderboard createEmptyLeaderBoard(String username) {
        Leaderboard leaderBoard = new Leaderboard();
        leaderBoard.setUsername(username);
        leaderBoard.setScores(new ArrayList<>());

        return leaderboardRepository.save(leaderBoard);
    }

    public Optional<Leaderboard> getById(String username) {
        Optional<Leaderboard> optionalLeaderboard = leaderboardRepository.findById(username);
        optionalLeaderboard.ifPresent(leaderboard -> {
            List<Score> scores = leaderboard.getScores();
            Collections.reverse(scores);
            leaderboard.setScores(scores);
        });
        return optionalLeaderboard;
    }
}
