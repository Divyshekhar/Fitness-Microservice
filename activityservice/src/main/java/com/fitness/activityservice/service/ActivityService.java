package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routing;


    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValid = userValidationService.validateUser(request.getUserId());
        if(!isValid){
            throw new RuntimeException("Invalid user" + request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .activityType(request.getActivityType())
                .duration(request.getDuration())
                .caloriesBurnt(request.getCaloriesBurnt())
                .startTime(request.getStartTime())
                .additionalMetric(request.getAdditionalMetrics())
                .build();
        Activity saved = activityRepository.save(activity);
        // publish to rabbitmq for ai processing
        try{
            rabbitTemplate.convertAndSend(exchange, routing, saved);
        }catch(Exception e){
            log.error("Failed to publish activity to rabbitmq ", e);
        }


        return mapToResponse(saved);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse res = new ActivityResponse();
        res.setId(activity.getId());
        res.setActivityType(activity.getActivityType());
        res.setCreatedAt(activity.getCreatedAt());
        res.setUpdatedAt(activity.getUpdatedAt());
        res.setDuration(activity.getDuration());
        res.setCaloriesBurnt(activity.getCaloriesBurnt());
        res.setAdditionalMetric(activity.getAdditionalMetric());
        res.setUserId(activity.getUserId());
        res.setStartTime(activity.getStartTime());
        return res;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("No activity with this id"));
    }
}
