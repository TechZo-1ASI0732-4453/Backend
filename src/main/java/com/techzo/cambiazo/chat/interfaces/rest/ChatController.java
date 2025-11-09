package com.techzo.cambiazo.chat.interfaces.rest;

import com.techzo.cambiazo.chat.application.dtos.ActiveConversation;
import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import com.techzo.cambiazo.chat.application.dtos.ConversationSummary;
import com.techzo.cambiazo.chat.application.services.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Chat", description = "Chat Management Endpoints")
@RequestMapping("/api/v2/chat")
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    public ChatController(SimpMessagingTemplate template, ChatService chatService) {
        this.template = template;
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {
        String cid = chatService.ensureConversation(message.getConversationId());
        if (message.getConversationId() == null || !cid.equals(message.getConversationId())) {
            message.setConversationId(cid);
        }

        if ("closed".equalsIgnoreCase(chatService.getConversationStatus(cid))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conversation is closed");
        }

        if (message.getType() == ChatMessage.MessageType.LOCATION) {
            if (message.getLatitude() == null || message.getLongitude() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Latitude and longitude are required for LOCATION messages"
                );
            }
        }

        if (message.getId() == null || message.getId().isEmpty()) {
            message.setId(UUID.randomUUID().toString());
        }

        if (message.getTimestamp() == null) {
            message.setTimestamp(new Date());
        }

        chatService.addMessage(cid, message);
        template.convertAndSend("/topic/chat." + cid, message);

        ActiveConversation senderActive   = chatService.getActiveConversationFor(message.getSenderId(), cid);
        ActiveConversation receiverActive = chatService.getActiveConversationFor(message.getReceiverId(), cid);

        template.convertAndSend("/topic/inbox." + message.getSenderId(), senderActive);
        template.convertAndSend("/topic/inbox." + message.getReceiverId(), receiverActive);
    }

    @PostMapping("/conversations/open")
    public String openConversation(@RequestParam(required = false) String conversationId,
                                   @RequestParam(required = false) String exchangeId) {
        String cid = chatService.ensureConversation(conversationId);
        if (exchangeId != null && !exchangeId.isEmpty()) {
            chatService.setConversationExchangeId(cid, exchangeId);
        }
        return cid;
    }

    @PostMapping("/conversations/{conversationId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeConversation(@PathVariable String conversationId) {
        chatService.closeConversation(conversationId);
    }

    @GetMapping("/conversations/{conversationId}/status")
    public String getConversationStatus(@PathVariable String conversationId) {
        return chatService.getConversationStatus(conversationId);
    }

    @GetMapping("/conversations")
    public List<ConversationSummary> listAllConversations() {
        return chatService.getAllConversationSummaries();
    }

    @GetMapping("/active/{userId}")
    public List<ActiveConversation> getActiveConversations(@PathVariable String userId) {
        return chatService.getActiveConversations(userId);
    }

    @PostMapping("/read/{userId}/{conversationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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