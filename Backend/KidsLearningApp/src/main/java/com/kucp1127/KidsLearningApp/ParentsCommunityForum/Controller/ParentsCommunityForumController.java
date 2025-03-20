package com.kucp1127.KidsLearningApp.ParentsCommunityForum.Controller;

import com.kucp1127.KidsLearningApp.ParentsCommunityForum.DataTransferObjects.SharedInfoDto;
import com.kucp1127.KidsLearningApp.ParentsCommunityForum.Model.ParentsCommunityForumModel;
import com.kucp1127.KidsLearningApp.ParentsCommunityForum.Service.ParentsCommunityForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communityforum")
public class ParentsCommunityForumController {


    @Autowired
    private  ParentsCommunityForumService parentsCommunityForumService;


    @GetMapping("/{username}")
    public ResponseEntity<?> getForum(@PathVariable String username) {
        List<ParentsCommunityForumModel> forum = parentsCommunityForumService.getForumByUsername(username);
        if (forum == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(forum);
    }



    @PostMapping("/{username}/sharedinfo")
    public ResponseEntity<ParentsCommunityForumModel> addSharedInfo(@PathVariable String username, @RequestBody SharedInfoDto sharedInfo) {
        ParentsCommunityForumModel updatedForum = parentsCommunityForumService.addSharedInfo(username, sharedInfo);
        return ResponseEntity.ok(updatedForum);
    }
}
