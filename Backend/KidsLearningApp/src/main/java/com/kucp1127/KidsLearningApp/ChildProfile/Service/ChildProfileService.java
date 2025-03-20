package com.kucp1127.KidsLearningApp.ChildProfile.Service;



import com.kucp1127.KidsLearningApp.ChildProfile.Model.ChildProfile;
import com.kucp1127.KidsLearningApp.ChildProfile.Repository.ChildProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChildProfileService {

    @Autowired
    private ChildProfileRepository childProfileRepository;

    @Autowired
    private GameRecordService gameRecordService;


    public Optional<ChildProfile> getById(String username) {
        return Optional.of(childProfileRepository.getById(username));
    }

    public Optional<ChildProfile> postChildProfile(ChildProfile childProfile) {
        return Optional.of(childProfileRepository.save(childProfile));
    }


}
