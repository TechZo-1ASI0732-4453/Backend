//package com.techzo.cambiazo.config;
//
//import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisTemplate<String, ChatMessage> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, ChatMessage> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        return template;
//    }
//}
