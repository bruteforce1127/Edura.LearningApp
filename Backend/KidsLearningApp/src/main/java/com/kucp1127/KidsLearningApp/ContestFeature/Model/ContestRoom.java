package com.kucp1127.KidsLearningApp.ContestFeature.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomCode;

    private LocalDateTime localDateTime;

    private String creator;

    @OneToMany(mappedBy = "contestRoom", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Participant> participants;

    // Set to track which usernames have attempted the quiz.
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> attemptedParticipants = new HashSet<>();
}
