package com.techzo.cambiazo.chat.interfaces.rest;

import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import com.techzo.cambiazo.chat.application.services.InMemoryChatService;
//import com.techzo.cambiazo.chat.application.services.RedisChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;
    private final InMemoryChatService chatService;
//    private final RedisChatService redisChatService;
//  RedisChatService redisChatService
    public ChatController(SimpMessagingTemplate template, InMemoryChatService chatService) {
        this.template = template;
        this.chatService = chatService;
//        this.redisChatService = redisChatService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {
        // Guarda el mensaje en Redis
//        redisChatService.addMessage(message.getConversationId(), message);
        chatService.addMessage(message.getConversationId(), message);
        template.convertAndSend("/topic/chat." + message.getConversationId(), message);
    }

    @MessageMapping("/chat.clear")
    public void clearChat(String conversationId) {
//        redisChatService.clearChat(conversationId);
        chatService.clearChat(conversationId);
    }
}
