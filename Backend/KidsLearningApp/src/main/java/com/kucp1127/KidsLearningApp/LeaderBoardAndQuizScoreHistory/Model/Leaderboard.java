package com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Leaderboard {
    @Id
    private String username;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "leaderboard_username")
    private List<Score> scores = new ArrayList<>();
}
