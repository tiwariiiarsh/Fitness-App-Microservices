package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service   // 👉 Ye class Spring Bean hai (AI-SERVICE ka consumer)
@Slf4j    // 👉 Lombok: logger object (log.info(), log.error())
@RequiredArgsConstructor // 👉 Lombok: constructor injection (future dependencies ke liye)
public class ActivityMessageListener {

    private final ActivityAiService aiService;
    private final RecommendationRepo recommendationRepo;

    // 👉 @RabbitListener batata hai ki ye method RabbitMQ queue ko listen karega
    // "activity.queue" wahi queue hai jo RabbitMqConfig me define ki gayi thi
    // Jab bhi is queue me message aayega, ye method automatically trigger ho jayega
    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {

        // 👉 RabbitMQ message JSON se Activity object me convert ho chuka hota hai
        // (Jackson2JsonMessageConverter ki wajah se)

        // 👉 Yahan actual business logic likhte ho
        // jaise: recommendation banana, ML processing, analysis, etc.
        log.info("Received activity for processing: {}", activity.getId());
//        log.info("Ai Recommendation is generated: {}", aiService.generateRecommendation(activity));
        Recommendation generateRecommendation = aiService.generateRecommendation(activity);
        recommendationRepo.save(generateRecommendation);
    }
}
