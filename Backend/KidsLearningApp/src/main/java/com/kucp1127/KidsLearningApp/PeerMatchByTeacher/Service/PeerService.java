package com.kucp1127.KidsLearningApp.PeerMatchByTeacher.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
public class PeerService {

    private final WebClient webClient;

    @Value("${adarsh.api.key}")
    private String apiKey;

    public PeerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    public Optional<Object> getResponse(Map<String, Double> studentAccuracyMap) {
        // Build a simple string representation of the data.
        StringBuilder dataBuilder = new StringBuilder();
        for (Map.Entry<String, Double> entry : studentAccuracyMap.entrySet()) {
            dataBuilder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append(", ");
        }
        if (dataBuilder.length() > 2) {
            dataBuilder.setLength(dataBuilder.length() - 2); // remove trailing comma and space
        }
        String dataString = dataBuilder.toString();

        // Construct the prompt asking Gemini to pair students.
        String prompt = "Based on the following student accuracy data: " + dataString +
                ". Please pair the students into groups of two such that each pair consists of one lower-performing " +
                "student and one higher-performing student. Return the result as a a text mapping the lower-performing " +
                "student's name to the higher-performing student's name and also show the accuracy of both students beside name .";

        // Create the JSON request body. Note: We escape quotes for safety.
        String requestBody = "{ \"contents\": [ { \"role\": \"user\", \"parts\": [ { \"text\": \"" + escapeJson(prompt) + "\" } ] } ] }";

        try {
            // Gemini API endpoint for content generation.
            String url = "/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
            String responseBody = webClient.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Blocking call for simplicity in this example

            // Convert the response string into a JsonNode.
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);
            return Optional.of(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Helper method to escape double quotes in the prompt string.
    private String escapeJson(String text) {
        return text.replace("\"", "\\\"");
    }
}
