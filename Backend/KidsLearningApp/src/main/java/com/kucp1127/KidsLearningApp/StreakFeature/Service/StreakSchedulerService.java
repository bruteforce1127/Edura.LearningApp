package com.kucp1127.KidsLearningApp.StreakFeature.Service;

import com.kucp1127.KidsLearningApp.Email.EmailController.EmailController;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
import com.kucp1127.KidsLearningApp.StreakFeature.Model.StreakModel;
import com.kucp1127.KidsLearningApp.StreakFeature.Repository.StreakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StreakSchedulerService {


    @Autowired
    private StreakRepository streakRepository;

    @Autowired
    private ChildService childService;

    @Autowired
    private EmailController emailController;

    @Autowired
    private StreakService streakService;

    // This method will run every day at 10:00 PM.
    @Scheduled(cron = "0 0 22 * * ?")
    public void sendReminderEmails() {
        Optional<List<Child>> childList = childService.getAllChild();
        if(childList.isPresent()){
            for(Child child : childList.get()){
                String username = child.getUsername();
                Optional<StreakModel> streakModel = streakRepository.findById(username);
                LocalDate currentDate = LocalDate.now();
                if(streakModel.isPresent()){
                    LocalDate storedDate = streakModel.get().getLocalDate();
                    if(!currentDate.equals(storedDate)){
                        String email = child.getEmail();
                        emailController.sendEmail(email);
                    }
                }
                else{
                    streakService.setFirstTime(username);
                }
            }

        }
    }

    // This method will run every day at 11:59 PM.
    @Scheduled(cron = "0 59 23 * * ?")
    public void resetStreaks() {
        Optional<List<Child>> childList = childService.getAllChild();
        if(childList.isPresent()){
            List<String> childUsernames = new ArrayList<>();
            for(Child child : childList.get()){
                String username = child.getUsername();
                Optional<StreakModel> streakModel = streakRepository.findById(username);
                LocalDate currentDate = LocalDate.now();
                if(streakModel.isPresent()){
                    LocalDate storedDate = streakModel.get().getLocalDate();
                    if(!currentDate.equals(storedDate)){
                        Optional<StreakModel> streakModel1 = streakRepository.findById(username);
                        if(streakModel1.isPresent()){
                            streakModel1.get().setStreak(0);
                            streakRepository.save(streakModel1.get());
                        }
                    }
                }
                else{
                    streakService.setFirstTime(username);
                }
            }

        }
    }
}
