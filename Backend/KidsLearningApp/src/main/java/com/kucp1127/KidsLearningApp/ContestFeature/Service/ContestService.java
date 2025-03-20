package com.kucp1127.KidsLearningApp.ContestFeature.Service;

import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
import com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects.CreateRoomRequest;
import com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects.JoinRoomRequest;
import com.kucp1127.KidsLearningApp.ContestFeature.DataTransferObjects.ScoreSubmissionRequest;
import com.kucp1127.KidsLearningApp.ContestFeature.Model.*;
import com.kucp1127.KidsLearningApp.ContestFeature.Repository.ContestRoomRepository;
import com.kucp1127.KidsLearningApp.ContestFeature.Repository.ParticipantRepository;
import com.kucp1127.KidsLearningApp.ContestFeature.Repository.RoomIdRepository;
import com.kucp1127.KidsLearningApp.Email.EmailController.EmailController;
import com.kucp1127.KidsLearningApp.Email.EmailModel.EmailRequestForContest;
import com.kucp1127.KidsLearningApp.PracticeQuizes.Model.PracticeQuestionModel;
import com.kucp1127.KidsLearningApp.PracticeQuizes.Service.PracticeQuestionModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContestService {

    @Autowired
    private ContestRoomRepository contestRoomRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EmailController emailController;

    @Autowired
    private ChildService childService;

    @Autowired
    private PracticeQuestionModelService practiceQuestionModelService;


    @Autowired
    private RoomIdRepository roomIdRepository;

    private String generateRoomCode() {
        int length = 6;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Transactional
    public ContestRoom createRoom(CreateRoomRequest request) {
        // 1) Create and save the ContestRoom
        ContestRoom room = new ContestRoom();
        String roomId = generateRoomCode();
        room.setRoomCode(roomId);
        room.setCreator(request.getCreator());
        room.setLocalDateTime(LocalDateTime.now());
        room.setParticipants(new ArrayList<>());
        ContestRoom savedRoom = contestRoomRepository.save(room);

        // 2) Send email invitations
        EmailRequestForContest emailRequestForContest = new EmailRequestForContest();
        List<String> participants = request.getParticipants();
        for (String participant : participants) {
            Optional<Child> child = childService.getChildByUserName(participant);
            if (child.isPresent()) {
                String email = child.get().getEmail();
                emailRequestForContest.setRoomId(roomId);
                emailRequestForContest.setCreator(request.getCreator());
                emailRequestForContest.setToEmail(email);
                emailRequestForContest.setName(participant);
                emailController.sendEmailForContest(emailRequestForContest);
            }
        }

        // 3) Prepare the questions
        int noOfQuestions = request.getNoOfQuestions();
        RoomIdQuestions roomIdQuestions = new RoomIdQuestions();
        roomIdQuestions.setRoomId(roomId);

        // First, persist the RoomIdQuestions so it's in a managed state
        RoomIdQuestions savedRoomIdQuestions = roomIdRepository.save(roomIdQuestions);

        // Retrieve and shuffle the practice questions for the specified grade
        List<PracticeQuestionModel> practiceQuestionModelList =
                practiceQuestionModelService.getByGrade(request.getGrade());
        Collections.shuffle(practiceQuestionModelList);
        // Build the ContestQuestions list
        List<ContestQuestions> questions = new ArrayList<>();
        for (int i = 0; i < Math.min(noOfQuestions, practiceQuestionModelList.size()); i++) {
            PracticeQuestionModel practiceQuestionModel = practiceQuestionModelList.get(i);
            ContestQuestions contestQuestion = new ContestQuestions();
            contestQuestion.setQuestionId(practiceQuestionModel.getId());
            // Link to the persisted RoomIdQuestions
            contestQuestion.setRoom(savedRoomIdQuestions);
            questions.add(contestQuestion);
        }

        // Assign the questions to the room
        savedRoomIdQuestions.setContestQuestions(questions);

        // Because of cascade = CascadeType.ALL, saving roomIdQuestions will also save ContestQuestions
        roomIdRepository.save(savedRoomIdQuestions);

        return savedRoom;
    }



    // Joining Room
    @Transactional
    public ContestRoom joinRoom(JoinRoomRequest request) throws Exception {
        Optional<ContestRoom> optionalRoom = contestRoomRepository.findByRoomCode(request.getRoomCode());
        if (!optionalRoom.isPresent()) {
            throw new Exception("Room not found");
        }
        ContestRoom room = optionalRoom.get();

        Participant participant = new Participant();
        participant.setUsername(request.getUsername());
        participant.setScore(0);
        participant.setContestRoom(room);

        room.getParticipants().add(participant);
        participantRepository.save(participant);
        contestRoomRepository.save(room);

        return room;
    }

    @Transactional
    public Optional<List<ContestQuestions>> startContest(String roomCode) {
        Optional<RoomIdQuestions> roomIdQuestions =  roomIdRepository.findById(roomCode);
        return roomIdQuestions.map(idQuestions -> Optional.ofNullable(idQuestions.getContestQuestions())).orElse(null);
    }


    public ContestRoom getContestRoom(String roomCode) throws Exception {
        Optional<ContestRoom> optionalRoom = contestRoomRepository.findByRoomCode(roomCode);
        if (!optionalRoom.isPresent()) {
            throw new Exception("Room not found");
        }
        return optionalRoom.get();
    }


    @Transactional
    public Participant updateScore(ScoreSubmissionRequest request) throws Exception {
        ContestRoom room = getContestRoom(request.getRoomCode());
        Optional<Participant> optionalParticipant = room.getParticipants().stream()
                .filter(p -> p.getUsername().equals(request.getUsername()))
                .findFirst();
        if (!optionalParticipant.isPresent()) {
            throw new Exception("Participant not found in the room");
        }
        Participant participant = optionalParticipant.get();
        participant.setScore(request.getScore());
        room.getAttemptedParticipants().add(request.getUsername());
        contestRoomRepository.save(room);
        return participant;
    }

    public List<Participant> getResult(String roomCode){
        Optional<ContestRoom> contestRoom = contestRoomRepository.findByRoomCode(roomCode);
        return contestRoom.map(ContestRoom::getParticipants).orElse(null);
    }


}
