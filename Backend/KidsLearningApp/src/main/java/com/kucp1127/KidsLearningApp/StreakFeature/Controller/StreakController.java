package com.kucp1127.KidsLearningApp.StreakFeature.Controller;


import com.kucp1127.KidsLearningApp.StreakFeature.DTO.StreakDto;
import com.kucp1127.KidsLearningApp.StreakFeature.Service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/streak")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getStreak(@PathVariable String username){
        int streak = streakService.getStreak(username);
        int longestStreak = streakService.getLongestStreak(username);
        StreakDto streakDto = new StreakDto();
        streakDto.setLongestStreak(longestStreak);
        streakDto.setStreak(streak);
        return  ResponseEntity.ok(streak);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> setStreak(@PathVariable String username){
        String s = streakService.setStreak(username);
        return ResponseEntity.ok(s);
    }


}
