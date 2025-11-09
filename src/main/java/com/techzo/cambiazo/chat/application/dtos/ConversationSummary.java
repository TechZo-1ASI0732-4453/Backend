package com.techzo.cambiazo.chat.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationSummary {
    private String conversationId;
    private String senderId;
    private String receiverId;
    private String exchangeId;
}