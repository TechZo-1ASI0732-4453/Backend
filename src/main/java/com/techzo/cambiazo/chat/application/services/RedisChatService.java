//package com.techzo.cambiazo.chat.application.services;
//
//import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.List;
//
//@Service
//public class RedisChatService {
//    private final RedisTemplate<String, ChatMessage> redisTemplate;
//
//    public RedisChatService(RedisTemplate<String, ChatMessage> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void addMessage(String conversationId, ChatMessage message) {
//        redisTemplate.opsForList().rightPush("chat:" + conversationId, message);
//        redisTemplate.expire("chat:" + conversationId, Duration.ofMinutes(30)); // expira en 30 min
//    }
//
//    public List<ChatMessage> getMessages(String conversationId) {
//        return redisTemplate.opsForList().range("chat:" + conversationId, 0, -1);
//    }
//
//    public void clearChat(String conversationId) {
//        redisTemplate.delete("chat:" + conversationId);
//    }
//}
