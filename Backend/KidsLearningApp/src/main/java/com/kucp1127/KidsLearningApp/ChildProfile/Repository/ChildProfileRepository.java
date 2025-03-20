package com.kucp1127.KidsLearningApp.ChildProfile.Repository;



import com.kucp1127.KidsLearningApp.ChildProfile.Model.ChildProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildProfileRepository extends JpaRepository<ChildProfile, String> {

}
