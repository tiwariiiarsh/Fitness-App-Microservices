package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepo recommendationRepo;


    public List<Recommendation> getUserRecommentation(String userId) {
        List<Recommendation> recommendations = recommendationRepo.findByUserId(userId);
        return recommendations;
    }

    public Recommendation getActivityRecommendation(String activityId) {
        Recommendation recommendation = recommendationRepo.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No Recommendation found for this activityId"));
        return recommendation;
    }
}
