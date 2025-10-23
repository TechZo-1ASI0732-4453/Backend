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
        String cid = chatService.ensureConversation(
                message.getConversationId(),
                message.getSenderId(),
                message.getReceiverId()
        );
        if (!cid.equals(message.getConversationId())) {
            message.setConversationId(cid);
        }

        chatService.addMessage(cid, message);

        template.convertAndSend("/topic/chat." + cid, message);

        ActiveConversation senderActive = chatService.getActiveConversationFor(message.getSenderId(), cid);
        ActiveConversation receiverActive = chatService.getActiveConversationFor(message.getReceiverId(), cid);

        template.convertAndSend("/topic/inbox." + message.getSenderId(), senderActive);
        template.convertAndSend("/topic/inbox." + message.getReceiverId(), receiverActive);
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
        ActiveConversation updated = chatService.getActiveConversationFor(userId, conversationId);
        template.convertAndSend("/topic/inbox." + userId, updated);
    }

    @GetMapping("/messages/{conversationId}")
    public List<ChatMessage> getMessages(@PathVariable String conversationId) {
        return chatService.getMessages(conversationId);
    }
}
