package com.kucp1127.KidsLearningApp.Security_Configuration.Service;


import com.kucp1127.KidsLearningApp.Security_Configuration.Repository.ParentRepository;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Parents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    public Optional<List<Parents>> getAllParent(){
        return Optional.of(parentRepository.findAll());
    }

    public Optional<Parents> getParentByUserName(String username){
        return parentRepository.findById(username);
    }

    public Parents postParent(Parents parents){
        return parentRepository.save(parents);
    }

}
