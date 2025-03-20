package com.kucp1127.KidsLearningApp.Security_Configuration.Service;


import com.kucp1127.KidsLearningApp.Security_Configuration.Repository.ChildRepository;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    public Optional<List<Child>> getAllChild(){
        return Optional.of(childRepository.findAll());
    }

    public Optional<Child> getChildByUserName(String username){
        return childRepository.findById(username);
    }

    public Child postChild(Child child){
        return childRepository.save(child);
    }


    public List<Child> getByChildGrade(int grade) {
        return childRepository.getChildByGrade(grade);
    }
}
