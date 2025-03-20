package com.kucp1127.KidsLearningApp.Email.EmailController;


import com.kucp1127.KidsLearningApp.Email.EmailModel.EmailRequestForContest;
import com.kucp1127.KidsLearningApp.Email.EmailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;


    public String sendEmail(String toEmail) {
        emailService.sendSimpleEmail(
                toEmail,
                "🚨 Oh no! Your daily streak is at risk! 🚨",
                "Hey there! 👋\n\nIt looks like your child hasn’t completed today’s challenge yet, " +
                        "and their awesome streak is in danger of breaking!" +
                        " 😱 Streaks are a fun way to keep the learning momentum going, " +
                        "and we know how much progress they’ve made so far. " +
                        "🌟\n\nDon’t worry—there’s still time to save the streak! " +
                        "Encourage them to jump back in and tackle today’s challenge." +
                        " Every day counts towards their learning journey, and we’re cheering them on!" +
                        " 📚✨\n\nLet’s get back on track together! 💪"
        );
        return "Email sent successfully";
    }



    public String sendEmailForContest(@RequestBody EmailRequestForContest emailRequest) {
        String subject = "🎉 You're Invited to a Fun Contest! 🎉";
        String message = "Hey " + emailRequest.getName() + "! 👋\n\n" +
                "You  have been invited by " + emailRequest.getCreator() + "  to join an exciting contest! 🎯✨\n\n" +
                "Get ready to challenge yourself and have lots of fun." +
                " Don't miss out! Your contest ID is: " + emailRequest.getRoomId() + " 🏆\n\n" +
                "See you in the contest! 🎮\n\n" +
                "Best regards,\n EduraTeam";

        emailService.sendSimpleEmail(emailRequest.getToEmail(), subject, message);
        return "Email sent successfully";
    }




}
