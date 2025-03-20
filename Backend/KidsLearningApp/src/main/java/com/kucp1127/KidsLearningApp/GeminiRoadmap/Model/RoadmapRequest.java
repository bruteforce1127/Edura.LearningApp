package com.kucp1127.KidsLearningApp.GeminiRoadmap.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoadmapRequest {
    private String username;
    private String studentClass;
    private double addition;
    private double subtraction;
    private double multiplication;
    private double division;
    private double reasoning;
    private double gk;
    private double science;
}
