package com.kucp1127.KidsLearningApp.GeminiRoadmap.Controller;


import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
import com.kucp1127.KidsLearningApp.GeminiRoadmap.Model.RoadmapRequest;
import com.kucp1127.KidsLearningApp.GeminiRoadmap.Service.GeminiService;
import com.kucp1127.KidsLearningApp.LeaderBoardAndQuizScoreHistory.Service.LeaderboardService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Optional;

@RestController
@RequestMapping("/generate-roadmap")
@CrossOrigin("*")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;


    @Autowired
    private LeaderboardService leaderboardService;


    @Autowired
    private ChildService childService;

    @PostMapping("/{username}")
    public Mono<ResponseEntity<String>> generateRoadmap(@PathVariable String username) {


        RoadmapRequest request = new RoadmapRequest();

        request.setUsername(username);
        request.setGk(leaderboardService.getAverageScoreMessage(username,"generalKnowledge"));
        request.setScience(leaderboardService.getAverageScoreMessage(username,"Science"));
        request.setMultiplication(leaderboardService.getAverageScoreMessage(username,"multiplication"));
        request.setAddition(leaderboardService.getAverageScoreMessage(username,"addition"));
        request.setReasoning(leaderboardService.getAverageScoreMessage(username,"reasoning"));
        request.setSubtraction(leaderboardService.getAverageScoreMessage(username,"subtraction"));
        request.setDivision(leaderboardService.getAverageScoreMessage(username,"division"));


        Optional<Child> child = childService.getChildByUserName(username);

        if(child.isPresent()){
            Child child1 = child.get();
            request.setStudentClass(String.valueOf(child1.getGrade()));
        }

        return geminiService.generateRoadmap(
                        request.getUsername(),
                        request.getStudentClass(),
                        request.getAddition(),
                        request.getSubtraction(),
                        request.getMultiplication(),
                        request.getDivision(),
                        request.getReasoning(),
                        request.getGk(),
                        request.getScience())
                .map(response -> ResponseEntity.ok(response));
    }



    private String extractTextFromResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray candidates = jsonResponse.getJSONArray("candidates");
            if (!candidates.isEmpty()) {
                JSONObject firstCandidate = candidates.getJSONObject(0);
                JSONObject content = firstCandidate.getJSONObject("content");
                JSONArray parts = content.getJSONArray("parts");
                if (!parts.isEmpty()) {
                    JSONObject firstPart = parts.getJSONObject(0);
                    return firstPart.getString("text");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No text content found in the response.";
    }




}
