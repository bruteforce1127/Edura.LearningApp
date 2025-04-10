package com.kucp1127.KidsLearningApp.ImageProcessing.Repository;


import com.fasterxml.jackson.databind.JsonNode;
import com.kucp1127.KidsLearningApp.ImageProcessing.Service.GeminiApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping("/gemini")
@CrossOrigin("*")
public class GeminiApiController {

    private static final Logger LOG = Logger.getLogger(GeminiApiController.class.getName());

    @Autowired
    private GeminiApiService geminiApiService;

    @PostMapping("/process-image")
    public ResponseEntity<HashMap<String, Object>> processImage(@RequestParam("file") MultipartFile file) {


        String prompt = "Examine the provided image and respond in a fun," +
                " kid-friendly tone using plenty of emojis. " +
                "If the image shows a toy or game, explain how to play with it," +
                " when it was developed, and who created it. If it shows a person," +
                " describe who that person is and what they do. If it shows an object, " +
                "explain its real-life significance and provide some real-life examples. " +
                "Make sure your answer is clear, engaging, and full of emojis!";

        try {
            JsonNode result = geminiApiService.getResponse(file, prompt);

            if(result != null) {
                return getResponseFormat(HttpStatus.OK, "Image processed successfully", result);
            } else {
                return getResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR, "Image processing failed", null);
            }
        } catch (Exception e) {
            return getResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR, "Critical Error: " + e.getLocalizedMessage(), null);
        }
    }

    public ResponseEntity<HashMap<String, Object>> getResponseFormat(HttpStatus status, String message, Object data) {
        int responseStatus = (status.equals(HttpStatus.OK)) ? 1 : 0;

        HashMap<String, Object> map = new HashMap<>();
        map.put("responseCode", responseStatus);
        map.put("message", message);
        map.put("data", data);
        return ResponseEntity.status(status).body(map);
    }
}