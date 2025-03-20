package com.kucp1127.KidsLearningApp.StreakFeature.Repository;


import com.kucp1127.KidsLearningApp.StreakFeature.Model.StreakModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreakRepository extends JpaRepository<StreakModel ,String> {
}
