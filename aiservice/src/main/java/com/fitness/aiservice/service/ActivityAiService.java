package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.detail;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {}",aiResponse);
        processAiResponse(activity,aiResponse);
        return processAiResponse(activity,aiResponse );
    }

    private Recommendation processAiResponse(Activity activity,String aiResponse){
        try{
            ObjectMapper mapper = new ObjectMapper(); //✔1 JSON → Java Object ,✔2 Java Object → JSON,✔3 JSON → JsonNode, pr yha  ✔ JSON → JsonNode ye kr rha h

            JsonNode rootNode = mapper.readTree(aiResponse); //JSON String → Tree structure me convert karta hai and Parsed JSON ko hold & navigate karta hai and give Value (text, number, child node)
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String JsonContent = textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("/n```","")
                    .trim();

            log.info(" PARSED RESPONSE FROM AI: {}",JsonContent );


            JsonNode analysisJson = mapper.readTree(JsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall:");
            addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace:");
            addAnalysisSection(fullAnalysis,analysisNode,"heartRate","Heart Rate:");
            addAnalysisSection(fullAnalysis,analysisNode,"caloriesBurned","Calories:");


            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafetyGuideline(analysisJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .safety(safety)
                    .suggestions(suggestions)
                    .createdAt(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return  createDefaultRecommendation(activity);
        }

    }

    public Recommendation createDefaultRecommendation(Activity activity){
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness professional"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyGuideline(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if (safetyNode.isArray()){
            safetyNode.forEach(s -> safety.add(s.asText()));
        }
        return safety.isEmpty() ?
                Collections.singletonList("No Specific safety is Provided!!"):
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion ->{
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s %s",workout,description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No Specific suggestions is Provided!!"):
               suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if (improvementsNode.isArray()){
            improvementsNode.forEach(improvement ->{
                String area = improvement.path("area").asText();
                String detail = improvement.path("recommendation").asText();
                improvements.add(String.format("%s %s",area,detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No Specific Improvements is Provided!!"):
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }


    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}
