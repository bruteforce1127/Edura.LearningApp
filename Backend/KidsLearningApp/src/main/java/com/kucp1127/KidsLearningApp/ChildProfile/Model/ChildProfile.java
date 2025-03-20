package com.kucp1127.KidsLearningApp.ChildProfile.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ChildProfile {

    // Using the username as the primary key
    @Id
    private String username;
    private int coins;


    @ElementCollection
    @CollectionTable(name = "child_unlocked_avatars", joinColumns = @JoinColumn(name = "child_username"))
    @Column(name = "avatar")
    private Set<String> unlockedAvatars = new HashSet<>();

    @OneToMany(mappedBy = "childProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GameRecord> gameRecords = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "daily_question_correct", joinColumns = @JoinColumn(name = "child_username"))
    private List<DailyQuestionCorrect> dailyQuestionCorrect;


    public void addAvatar(String avatar) {
        this.unlockedAvatars.add(avatar);
    }

}
