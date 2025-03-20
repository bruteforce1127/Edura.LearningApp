package com.kucp1127.KidsLearningApp.ParentsChatBot.Controller;


import com.kucp1127.KidsLearningApp.ParentsChatBot.Service.ParentChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("/chatbot")
public class ParentChatBotController {

    @Autowired
    private ParentChatBotService parentChatBotService;

    @PostMapping("/{username}")
    public Mono<ResponseEntity<?>> getChatOutput(@PathVariable String username,
                                                 @RequestBody String prompt){
        return parentChatBotService.
                generateChat(username,prompt).
                map(response -> ResponseEntity.ok(response));
    }

}
