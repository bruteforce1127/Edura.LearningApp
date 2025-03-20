package com.kucp1127.KidsLearningApp.GeminiRoadmap.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();

    }

    public Mono<String> generateRoadmap(String username, String studentClass,
                                        double addition, double subtraction, double multiplication,
                                        double division, double reasoning, double gk, double science) {

        // Construct the prompt using the variables
        String prompt = String.format(
                "the avg score for %s in addition is %.2f in subtraction it is %.2f in multiplication it is %.2f in division it is %.2f in reasoning it is %.2f in gk it is %.2f in science it is %.2f " +
                        "assume i am in class %s can you give me a road map that tells me my strong areas, my weak areas and tells me where i need to focus more and where i need to focus less " +
                        "use emojis efficiently. These scores are calculated out of 10 . Here if my class is 0 it means i am in kindergarten so refer my class as kindergarten when my class is 0 " +
                        "make your response kid friendly and take references from cartoon characters how they failed and learned and grew" ,
                username, addition, subtraction, multiplication, division, reasoning, gk, science, studentClass);

        // Build the JSON payload (matching the curl example)
        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);

        Map<String, Object> partsWrapper = new HashMap<>();
        partsWrapper.put("parts", Collections.singletonList(textPart));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Collections.singletonList(partsWrapper));

        // Call the Gemini API endpoint
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-2.0-flash:generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class);
    }
}
