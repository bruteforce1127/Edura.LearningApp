package com.kucp1127.KidsLearningApp.Security_Configuration.Repository;

import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child,String> {

    List<Child> getChildByGrade(int grade);
}
