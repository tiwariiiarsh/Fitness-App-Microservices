package com.example.activityService.controller;

import com.example.activityService.dto.ActivityRequest;
import com.example.activityService.dto.ActivityResponse;
import com.example.activityService.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/activities")
public class ActivityController {


    private ActivityService activityService;

    @PostMapping("")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request){
//        ,@RequestHeader ("X-User-ID") String userId
//        if (userId !=null){
//            request.setUserId(userId);
//        }
        return new ResponseEntity<ActivityResponse>(activityService.trackActivity(request), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String userId){
        return new ResponseEntity<List<ActivityResponse>>(activityService.getUserActivities(userId), HttpStatus.OK);
    }

    @GetMapping("{activityId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId){
        return new ResponseEntity<ActivityResponse>(activityService.getActivityById(activityId), HttpStatus.OK);
    }
}
