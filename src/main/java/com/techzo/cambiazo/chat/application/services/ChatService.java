package com.techzo.cambiazo.chat.application.services;

import com.techzo.cambiazo.chat.application.dtos.ActiveConversation;
import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import com.techzo.cambiazo.chat.application.dtos.ConversationSummary;
import java.util.List;

public interface ChatService {
    String ensureConversation(String conversationId);
    void addMessage(String conversationId, ChatMessage msg);
    List<ChatMessage> getMessages(String conversationId);
    void clearChat(String conversationId);

    List<ActiveConversation> getActiveConversations(String userId);
    void markConversationRead(String userId, String conversationId);
    ActiveConversation getActiveConversationFor(String userId, String conversationId);
    List<ConversationSummary> getAllConversationSummaries();

    List<String> getConversationIds(String userId);
    void closeConversation(String conversationId);
    String getConversationStatus(String conversationId);

    void setConversationExchangeId(String conversationId, String exchangeId);
    String getConversationExchangeId(String conversationId);
}
