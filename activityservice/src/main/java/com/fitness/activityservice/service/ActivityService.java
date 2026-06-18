package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .activityType(request.getActivityType())
                .duration(request.getDuration())
                .caloriesBurnt(request.getCaloriesBurnt())
                .startTime(request.getStartTime())
                .additionalMetric(request.getAdditionalMetrics())
                .build();
        Activity saved = activityRepository.save(activity);
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
