package com.kucp1127.KidsLearningApp.GamesDetails.Repository;


import com.kucp1127.KidsLearningApp.GamesDetails.Model.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<Games, String> {
    List<Games> findByGrade(int grade);
    List<Games> findByGradeAndCategory(int grade, String category);
}
