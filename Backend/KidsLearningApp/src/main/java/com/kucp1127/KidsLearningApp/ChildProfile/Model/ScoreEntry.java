package com.kucp1127.KidsLearningApp.ChildProfile.Model;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEntry {
    private LocalDateTime timestamp;
}
