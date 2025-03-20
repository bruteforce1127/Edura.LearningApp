package com.kucp1127.KidsLearningApp.GamesDetails.Model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Games {
    @Id
    private String gameUrl;
    private int grade;
    private String category;
    private String imageUrl;
    private String text;
    private String learning;
    private String stepsToPlay;

    @ElementCollection
    @CollectionTable(name = "game_video_urls", joinColumns = @JoinColumn(name = "game_url"))
    @Column(name = "video_url")
    private List<String> videoUrl;
}
