package com.kucp1127.KidsLearningApp.Security_Configuration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parents {

    @Id
    @Column(unique = true, nullable = false)
    private String username;


    @Column(nullable = false)
    private String fullName;


    @Column(unique = true, nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;

    private List<String> children;
}
