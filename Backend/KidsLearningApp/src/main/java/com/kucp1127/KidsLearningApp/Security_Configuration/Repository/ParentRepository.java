package com.kucp1127.KidsLearningApp.Security_Configuration.Repository;


import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Parents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParentRepository extends JpaRepository<Parents,String> {

}