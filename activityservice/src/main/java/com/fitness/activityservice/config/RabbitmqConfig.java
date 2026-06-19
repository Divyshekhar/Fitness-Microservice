package com.fitness.activityservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue activityQueue(){
        return new Queue("activity.queue", true);
    }
    @Bean
    public DirectExchange activityExchange(){
        return new DirectExchange("fitness.exchange");
    }
    @Bean
    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange){
        return BindingBuilder.bind(activityQueue).to(activityExchange).with("activity.tracking");
    }

    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }
}
