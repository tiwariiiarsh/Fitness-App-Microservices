package com.example.activityService.service;

import com.example.activityService.dto.ActivityRequest;
import com.example.activityService.dto.ActivityResponse;
import com.example.activityService.model.Activity;
import com.example.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor  //Generates a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.
public class ActivityService {

    private final UserValidateService userValidateService;
    private final ActivityRepository activityRepository;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;  //RabbitTemplate ka use RabbitMQ me message send (publish) karne ke liye hota hai.,Producer side ka main tool hai 🐇📤




    public ActivityResponse trackActivity(ActivityRequest request) {

//        String userId = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        request.setUserId(userId);


        boolean isValidUser = userValidateService.validateUser(request.getUserId());
        if (!isValidUser){
            throw new RuntimeException("Invalid User: "+ request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrices(request.getAdditionalMetrices())
                .build();
        Activity savedActivity = activityRepository.save(activity);

//        publish to rabbitmq for AI processing
        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
        }catch (Exception e){
            log.error("failed to publish activity to RabbitMQ: ",e);
        }
        return mapToResponse(savedActivity);
    }


    public ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrices(activity.getAdditionalMetrices());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;

    }


    public List<ActivityResponse> getUserActivities(String userId){
        List<Activity> activities = activityRepository.findByUserId(userId);
         return activities.stream()
                 .map(this::mapToResponse)
                 .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String userId) {
        Activity activity = activityRepository.findById(userId)
                .orElseThrow(() ->  new RuntimeException("Activity with this Id:  "+ userId+"  is not found!!"));
        return mapToResponse(activity);
    }
}
