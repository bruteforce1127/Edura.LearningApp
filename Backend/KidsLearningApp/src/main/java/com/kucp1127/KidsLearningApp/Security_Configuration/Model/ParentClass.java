package com.kucp1127.KidsLearningApp.Security_Configuration.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentClass {

    private String username;
    private String fullName;
    private String email;
    private String password;
    private List<Child> children = new ArrayList<>();
}
