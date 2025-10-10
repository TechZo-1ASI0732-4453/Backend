package com.techzo.cambiazo.chat.application.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Data
@Getter
@Setter
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String senderId;
    private String receiverId;
    private String conversationId;
    private String content;
    private Instant timestamp = Instant.now();

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String receiverId, String conversationId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.conversationId = conversationId;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
