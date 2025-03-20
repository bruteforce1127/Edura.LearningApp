package com.kucp1127.KidsLearningApp.ContestFeature.Repository;

import com.kucp1127.KidsLearningApp.ContestFeature.Model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
