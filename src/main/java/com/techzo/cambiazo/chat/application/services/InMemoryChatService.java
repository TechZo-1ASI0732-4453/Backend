package com.techzo.cambiazo.chat.application.services;

import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryChatService {

    private final Map<String, List<ChatMessage>> chats = new ConcurrentHashMap<>();

    public List<ChatMessage> getMessages(String conversationId) {
        return chats.getOrDefault(conversationId, new ArrayList<>());
    }

    public void addMessage(String conversationId, ChatMessage message) {
        chats.computeIfAbsent(conversationId, k -> new ArrayList<>()).add(message);
    }

    public void clearChat(String conversationId) {
        chats.remove(conversationId);
    }

    public void expireOldChats(Duration ttl) {
        // opcional: limpia chats inactivos
    }
}
