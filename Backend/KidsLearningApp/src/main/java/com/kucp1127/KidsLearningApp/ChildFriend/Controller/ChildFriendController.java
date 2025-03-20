//package com.kucp1127.KidsLearningApp.ChildFriend.Controller;
//
//
//import com.kucp1127.KidsLearningApp.ChildFriend.DataTransferObjects.ChildFriendDto;
//import com.kucp1127.KidsLearningApp.ChildProfile.Model.ChildProfile;
//import com.kucp1127.KidsLearningApp.ChildProfile.Service.ChildProfileService;
//import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
//import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@CrossOrigin("*")
//public class ChildFriendController {
//
//    @Autowired
//    private ChildProfileService childProfileService;
//
//    @Autowired
//    private ChildService childService;
//
//    @GetMapping("/friend/{username}")
//    public ResponseEntity<?> getFriend(@PathVariable String username){
//        Optional<ChildProfile> childProfile = childProfileService.getById(username);
//        if(childProfile.isPresent()){
//            ChildFriendDto childFriendDto = new ChildFriendDto();
//            childFriendDto.setCoins(childProfile.get().getCoins());
//            int grade = -1;
//            Optional<Child> child = childService.getChildByUserName(username);
//
//            if(child.isPresent()) grade = child.get().getGrade();
//            childFriendDto.setGrade(grade);
//
//            childFriendDto.setUsername(username);
////            childFriendDto.setNoOfGames();
//
//        }
//    }
//
//}
