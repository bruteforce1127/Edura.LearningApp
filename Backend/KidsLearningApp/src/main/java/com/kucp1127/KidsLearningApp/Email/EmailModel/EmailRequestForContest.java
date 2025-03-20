package com.kucp1127.KidsLearningApp.Email.EmailModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestForContest {

    private  String creator;
    private String toEmail;
    private String name;
    private String roomId;
}
