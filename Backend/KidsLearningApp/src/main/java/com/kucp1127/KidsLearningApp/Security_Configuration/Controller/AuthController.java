package com.kucp1127.KidsLearningApp.Security_Configuration.Controller;


import com.kucp1127.KidsLearningApp.ChildProfile.Model.ChildProfile;
import com.kucp1127.KidsLearningApp.ChildProfile.Service.ChildProfileService;
import com.kucp1127.KidsLearningApp.Security_Configuration.SecurityConfiguration.JwtService;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ParentService;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.ParentClass;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Parents;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.User;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Service.LeaderboardService;
import com.kucp1127.KidsLearningApp.StreakFeature.Service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildService childService;

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private StreakService streakService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ChildProfileService childProfileService;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder(12);

    @GetMapping("/parents")
    public ResponseEntity<?> getAllParents(){
        Optional<List<Parents>> parents = parentService.getAllParent();
        if(parents.isPresent()) return new ResponseEntity<>(parents.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/child")
    public ResponseEntity<?> getAllChild(){
        Optional<List<Child>> children = childService.getAllChild();
        if(children.isPresent()) return new ResponseEntity<>(children.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/child/{id}")
    public ResponseEntity<?> getChildByUserName(@PathVariable("id")  String username){
        Optional<Child> child = childService.getChildByUserName(username);
        if(child.isPresent()) return new ResponseEntity<>(child.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<?> getParentByUserName(@PathVariable("id")  String username){
        Optional<Parents> parent = parentService.getParentByUserName(username);
        if(parent.isPresent()) return new ResponseEntity<>(parent.get(), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/register")
    public void register(@RequestBody ParentClass parent){
        Parents parents = new Parents();
        parents.setEmail(parent.getEmail());
        parents.setUsername(parent.getUsername());
        parents.setFullName(parent.getFullName());
        parents.setPassword(bCryptPasswordEncoder.encode(parent.getPassword()));
//        parents.setGrade(10);
        List<String> userNames = new ArrayList<>();

        List<Child> children = parent.getChildren();

        for(Child ele : children){
            ele.setEmail(parent.getEmail());
            ele.setPassword(bCryptPasswordEncoder.encode(ele.getPassword()));
            childService.postChild(ele);
            ChildProfile childProfile = new ChildProfile();
            childProfile.setUsername(ele.getUsername());
            childProfile.setCoins(0);
            childProfile.setDailyQuestionCorrect(new ArrayList<>());
            childProfile.setGameRecords(new ArrayList<>());
            childProfile.setUnlockedAvatars(new HashSet<>());

            childProfileService.postChildProfile(childProfile);
            streakService.setFirstTime(ele.getUsername());
            leaderboardService.createEmptyLeaderBoard(ele.getUsername());

            userNames.add(ele.getUsername());
        }
        parents.setChildren(userNames);
        parentService.postParent(parents);

    }

    @GetMapping("/find/{username}")
    public Boolean findUsername(@PathVariable String username){
        Optional<Parents> parents = parentService.getParentByUserName(username);
        Optional<Child> child = childService.getChildByUserName(username);

        return parents.isPresent() || child.isPresent();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()) {
            Optional<Parents> parent = parentService.getParentByUserName(user.getUsername());
            if(parent.isPresent()){
//                Some work will be done
            }
            else{
                Optional<List<Child>> children = childService.getAllChild();
                if(children.isPresent()){
//                    Some work will be done
                }
            }
            System.out.println(jwtService.generateToken(user.getUsername()));
            return jwtService.generateToken(user.getUsername());
        }
        return "failure";
    }

    @GetMapping("/grade/{username}")
    public ResponseEntity<?> getGradeForLogin(@PathVariable String username){

        Optional<Child> child = childService.getChildByUserName(username);
        int grade = -10;
        if(child.isPresent()){
            grade = child.get().getGrade();
        }
        Optional<Parents> parent = parentService.getParentByUserName(username);
        if(parent.isPresent()) grade=-1;
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/getChild/{username}")
    public ResponseEntity<?> getChildList(@PathVariable String username){
        int grade = -10;
        List<String> child = new ArrayList<>();
        Optional<Parents> parent = parentService.getParentByUserName(username);
        if(parent.isPresent()) {
            child = parent.get().getChildren();
        }
        return ResponseEntity.ok(child);
    }

    @GetMapping("byGrade/{grade}")
    public ResponseEntity<?> getByGrade(@PathVariable int grade){
        List<Child> children = childService.getByChildGrade(grade);
        List<String> listOfUsernames = new ArrayList<>();
        for(Child child : children ){
            listOfUsernames.add(child.getUsername());
        }
        return ResponseEntity.ok(listOfUsernames);

    }


}
