package com.kucp1127.KidsLearningApp.ContestFeature.Controller;

import com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects.*;
import com.kucp1127.KidsLearningApp.ContestFeature.Model.*;
import com.kucp1127.KidsLearningApp.ContestFeature.Repository.ContestRoomRepository;
import com.kucp1127.KidsLearningApp.ContestFeature.Service.ContestService;
import com.kucp1127.KidsLearningApp.PracticeQuizes.Model.PracticeQuestionModel;
import com.kucp1127.KidsLearningApp.PracticeQuizes.Repository.PracticeQuestionModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contest")
@CrossOrigin("*")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestRoomRepository contestRoomRepository;

    @Autowired
    private PracticeQuestionModelRepository practiceQuestionModelRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request) {
        try {
            ContestRoom room = contestService.createRoom(request);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/start/{roomCode}/{username}")
    public ResponseEntity<?> startContest(@PathVariable String roomCode, @PathVariable String username) throws Exception {


        ContestRoom contestRoom = contestService.getContestRoom(roomCode);
        if (contestRoom.getAttemptedParticipants().contains(username)) {
            return ResponseEntity.badRequest().body("Quiz already attempted by " + username);
        }

        JoinRoomRequest roomRequest = new JoinRoomRequest();
        roomRequest.setRoomCode(roomCode);
        roomRequest.setUsername(username);
        ContestRoom room = contestService.joinRoom(roomRequest);



        contestRoom.getAttemptedParticipants().add(username);
        contestRoomRepository.save(contestRoom);
        Optional<List<ContestQuestions>> optionalContestQuestions = contestService.startContest(roomCode);
        if (optionalContestQuestions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        List<ContestQuestions> contestQuestionsList = optionalContestQuestions.get();
        List<PracticeQuestionModel> practiceQuestionModelList = new ArrayList<>();
        for (ContestQuestions cq : contestQuestionsList) {
            int practiceQId = cq.getQuestionId();  // The ID of the practice question
            Optional<PracticeQuestionModel> pmOpt = practiceQuestionModelRepository.findById(practiceQId);
            pmOpt.ifPresent(practiceQuestionModelList::add);
        }
        return ResponseEntity.ok(practiceQuestionModelList);
    }


    @GetMapping("/{roomCode}")
    public ResponseEntity<?> getContestRoom(@PathVariable String roomCode) {
        try {
            ContestRoom room = contestService.getContestRoom(roomCode);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/submitScore")
    public ResponseEntity<?> submitScore(@RequestBody ScoreSubmissionRequest request) {
        try {
            Participant participant = contestService.updateScore(request);
            return ResponseEntity.ok(participant);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/result/{roomCode}")
    public ResponseEntity<List<Result>> getResult(@PathVariable String roomCode){
        List<Participant> participantList = contestService.getResult(roomCode);
        List<Result> results = new ArrayList<>();
        if(participantList!=null){
            for(Participant participant : participantList){
                Result result = new Result();
                result.setScore(participant.getScore());
                result.setUsername(participant.getUsername());
                results.add(result);
            }
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/list/{username}")
    public ResponseEntity<?> getListOfRoomId(@PathVariable String username){


        List<ContestRoom> contestRoom = contestRoomRepository.findByCreator(username);

        List<RoomListDTO> roomListDTOS = new ArrayList<>();

        for(ContestRoom contestRoom1 : contestRoom){
            RoomListDTO roomListDTO = new RoomListDTO();
            roomListDTO.setLocalDateTime(contestRoom1.getLocalDateTime());
            roomListDTO.setRoomId(contestRoom1.getRoomCode());

            roomListDTOS.add(roomListDTO);

        }

        return ResponseEntity.ok(roomListDTOS);

    }


}
