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
public class ContestQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int questionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private RoomIdQuestions room;
}
