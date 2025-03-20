package com.kucp1127.KidsLearningApp.ParentsChatBot.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParentChatBotService {

    private final WebClient webClient;

    @Value("${adarsh.api.key}")
    private String apiKey;

    public ParentChatBotService(WebClient.Builder webClientBuilder) {
        // Base URL for the Gemini API
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    public Mono<String> generateChat(String username, String prompt) {


        String updatedPrompt = " "+ prompt +
                "And yeah if somehow you want to express about yourself or " +
                "want to mention yourself then remember you are Edura AI and also keep the language" +
                " very easy to understand and output must not be more than 150 words and " +
                "if someone asks you who made you then reply Adarsh dubey made me  mention this only when someone specifically asks you";

        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", updatedPrompt);

        Map<String, Object> partsWrapper = new HashMap<>();
        partsWrapper.put("parts", Collections.singletonList(textPart));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Collections.singletonList(partsWrapper));

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
