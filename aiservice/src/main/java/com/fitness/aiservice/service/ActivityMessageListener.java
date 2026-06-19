package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {
    private final ActivityAIService activityAIService;
    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        try{
            log.info("Received activity for processing: {}", activity.getId());
            log.info("Generated Recommendation: {}", activityAIService.generateRecommendation(activity));
        }catch(Exception e){
            log.error("Error occurred in processing activity in queue", e);
        }
    }
}
