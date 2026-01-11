package com.example.activityService.repository;

import com.example.activityService.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity, String> {


    List<Activity> findByUserId(String userId);
}
