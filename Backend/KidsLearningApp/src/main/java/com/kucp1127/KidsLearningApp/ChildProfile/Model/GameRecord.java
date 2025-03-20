package com.kucp1127.KidsLearningApp.ChildProfile.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRecord {



    @Id
    @Column(name = "games_url", nullable = false, unique = true)
    private String game;
    @ManyToOne
    @JoinColumn(name = "child_username")
    @JsonBackReference
    private ChildProfile childProfile;
    private String learning;
    @ElementCollection
    @CollectionTable(name = "game_score_history", joinColumns = @JoinColumn(name = "game_record_games_url"))
    private List<ScoreEntry> scoreHistory = new ArrayList<>();

}
