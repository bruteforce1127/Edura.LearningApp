package com.kucp1127.KidsLearningApp.StreakFeature.Service;


import com.kucp1127.KidsLearningApp.StreakFeature.Model.StreakModel;
import com.kucp1127.KidsLearningApp.StreakFeature.Repository.StreakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class StreakService {

    @Autowired
    private StreakRepository streakRepository;


    public int getStreak(String username) {
        Optional<StreakModel> streakModelOptional = streakRepository.findById(username);
        return streakModelOptional.map(StreakModel::getStreak).orElse(0);
    }

    public String setStreak(String username) {
        LocalDate currentDate = LocalDate.now();
        Optional<StreakModel> streakModelOptional = streakRepository.findById(username);
        if(streakModelOptional.isPresent()){
            if(!currentDate.equals(streakModelOptional.get().getLocalDate())){
                int streak = streakModelOptional.get().getStreak();
                streakModelOptional.get().setStreak(streakModelOptional.get().getStreak()+1);
                if(streak<streakModelOptional.get().getStreak()) streakModelOptional.get().setLongestStreak(streakModelOptional.get().getStreak());
                streakModelOptional.get().setLocalDate(currentDate);
                streakRepository.save(streakModelOptional.get());
                return "Streak updated";
            }
        }
        return "No need to update streak";
    }

    public void setFirstTime(String username){
        StreakModel streakModel = new StreakModel();
        streakModel.setUsername(username);
        streakModel.setStreak(1);
        streakModel.setLocalDate(LocalDate.now());
        streakModel.setLongestStreak(0);
        streakRepository.save(streakModel);
    }

    public int getLongestStreak(String username) {
        Optional<StreakModel> streakModelOptional = streakRepository.findById(username);
        return streakModelOptional.map(StreakModel::getLongestStreak).orElse(0);
    }
}
