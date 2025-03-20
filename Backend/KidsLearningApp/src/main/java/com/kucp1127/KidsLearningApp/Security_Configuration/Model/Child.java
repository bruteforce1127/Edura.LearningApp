package com.kucp1127.KidsLearningApp.Security_Configuration.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Child {
    @Id
    @Column(unique = true, nullable = false)
    private String username;
    private int grade;
    @Column(nullable = false)
    private String name;
    private String email;
    @Column(nullable = false)
    private String password;
    private int age;
}
