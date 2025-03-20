package com.kucp1127.KidsLearningApp.ContestFeature.Repository;

import com.kucp1127.KidsLearningApp.ContestFeature.Model.ContestRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestRoomRepository extends JpaRepository<ContestRoom, Long> {
    Optional<ContestRoom> findByRoomCode(String roomCode);

    List<ContestRoom> findByCreator(String creator);

}
