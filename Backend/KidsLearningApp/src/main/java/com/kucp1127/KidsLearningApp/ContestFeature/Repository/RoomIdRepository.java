package com.kucp1127.KidsLearningApp.ContestFeature.Repository;

import com.kucp1127.KidsLearningApp.ContestFeature.Model.RoomIdQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomIdRepository extends JpaRepository<RoomIdQuestions , String> {

}
