package com.techzo.cambiazo.chat.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveConversation {
    private String conversationId;
    private String peerId;
    private String lastMessage;
    private Date updatedAt;
    private int unreadCount;
    private String exchangeId;
}
