package com.kucp1127.KidsLearningApp.ContestFeature.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    private String username;
    @ManyToOne
    @JoinColumn(name = "contest_room_id")
    @JsonBackReference
    private ContestRoom contestRoom;
}