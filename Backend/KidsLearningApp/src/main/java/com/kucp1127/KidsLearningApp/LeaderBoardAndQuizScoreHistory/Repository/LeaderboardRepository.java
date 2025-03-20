package com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Repository;


import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, String> {
    // Additional query methods can be defined here if needed.
}
