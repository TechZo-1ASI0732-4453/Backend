package com.techzo.cambiazo.chat.interfaces.rest;

import com.techzo.cambiazo.chat.application.dtos.ActiveConversation;
import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import com.techzo.cambiazo.chat.application.services.InMemoryChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@Tag(name="Chat", description="Chat Management Endpoints")
@RequestMapping("/api/v2/chat")
public class ChatController {

    private final SimpMessagingTemplate template;
    private final InMemoryChatService chatService;

    public ChatController(SimpMessagingTemplate template, InMemoryChatService chatService) {
        this.template = template;
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {
        chatService.addMessage(message.getConversationId(), message);
        template.convertAndSend("/topic/chat." + message.getConversationId(), message);
    }

    @MessageMapping("/chat.clear")
    public void clearChat(String conversationId) {
        chatService.clearChat(conversationId);
    }

    @GetMapping("/active/{userId}")
    public List<ActiveConversation> getActiveConversations(@PathVariable String userId) {
        return chatService.getActiveConversations(userId);
    }

    @PostMapping("/read/{userId}/{conversationId}")
    public void markRead(@PathVariable String userId, @PathVariable String conversationId) {
        chatService.markConversationRead(userId, conversationId);
    }

    @GetMapping("/messages/{conversationId}")
    public List<ChatMessage> getMessages(@PathVariable String conversationId) {
        return chatService.getMessages(conversationId);
    }
}
