package com.mszlu.blog.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic_mszlu_exchange", true, false);
    }

    @Bean
    public Queue articleQueue() {
        return new Queue("article.queue", true);
    }


    @Bean
    public Binding articleBingding() {
        return BindingBuilder.bind(articleQueue()).to(topicExchange()).with("*.clearCache.*");
    }


}
