package com.techzo.cambiazo.chat.application.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum MessageType { TEXT, LOCATION }

    private String id;
    private String senderId;
    private String receiverId;
    private String conversationId;
    private String exchangeId;
    private String content;
    private MessageType type = MessageType.TEXT;
    private Double latitude;
    private Double longitude;
    private String locationLabel;

    private String timestamp;

    public ChatMessage() {}

    public ChatMessage(String senderId, String receiverId, String conversationId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.conversationId = conversationId;
        this.content = content;
        this.type = MessageType.TEXT;
        this.timestamp = Instant.now().toString();
    }

    public ChatMessage(String senderId, String receiverId, String conversationId, String exchangeId, String content) {
        this(senderId, receiverId, conversationId, content);
        this.exchangeId = exchangeId;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", exchangeId='" + exchangeId + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", locationLabel='" + locationLabel + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}