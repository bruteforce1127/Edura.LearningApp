package com.kucp1127.KidsLearningApp.ChildProfile.Repository;


import com.kucp1127.KidsLearningApp.ChildProfile.Model.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordRepository extends JpaRepository<GameRecord,String> {

}
