package com.techzo.cambiazo.chat.application.services;

import com.techzo.cambiazo.chat.application.dtos.ActiveConversation;
import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryChatService {

    private final Map<String, List<ChatMessage>> messagesByConversation = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> conversationsByUser = new ConcurrentHashMap<>();
    private final Map<String, ConversationMeta> conversationMeta = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Integer>> unreadMap = new ConcurrentHashMap<>();

    public String ensureConversation(String conversationId) {
        String cid = conversationId;
        if (cid == null || cid.isBlank()) {
            cid = UUID.randomUUID().toString();
        }
        messagesByConversation.computeIfAbsent(cid, k -> Collections.synchronizedList(new ArrayList<>()));
        conversationMeta.putIfAbsent(cid, new ConversationMeta(null, Instant.EPOCH));
        return cid;
    }

    public void addMessage(String conversationId, ChatMessage msg) {
        messagesByConversation
                .computeIfAbsent(conversationId, k -> Collections.synchronizedList(new ArrayList<>()))
                .add(msg);

        linkConversationToUser(msg.getSenderId(), conversationId);
        linkConversationToUser(msg.getReceiverId(), conversationId);

        conversationMeta.put(conversationId,
                new ConversationMeta(msg.getContent(), msg.getTimestamp() == null ? Instant.now() : msg.getTimestamp()));

        incrementUnread(msg.getReceiverId(), conversationId);
    }

    public List<ChatMessage> getMessages(String conversationId) {
        return messagesByConversation.getOrDefault(conversationId, Collections.emptyList());
    }

    public void clearChat(String conversationId) {
        messagesByConversation.remove(conversationId);
        conversationMeta.remove(conversationId);
        for (Map<String, Integer> m : unreadMap.values()) {
            m.remove(conversationId);
        }
    }

    public List<ActiveConversation> getActiveConversations(String userId) {
        Set<String> convs = conversationsByUser.getOrDefault(userId, Collections.emptySet());
        List<ActiveConversation> out = new ArrayList<>(convs.size());
        for (String cid : convs) {
            out.add(getActiveConversationFor(userId, cid));
        }
        out.sort((a, b) -> {
            Instant ia = a.getUpdatedAt() != null ? a.getUpdatedAt() : Instant.EPOCH;
            Instant ib = b.getUpdatedAt() != null ? b.getUpdatedAt() : Instant.EPOCH;
            return ib.compareTo(ia);
        });
        return out;
    }

    public void markConversationRead(String userId, String conversationId) {
        unreadMap.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(conversationId, 0);
    }

    public ActiveConversation getActiveConversationFor(String userId, String conversationId) {
        ConversationMeta meta = conversationMeta.get(conversationId);
        String peer = resolvePeer(userId, conversationId);
        int unread = unreadMap.getOrDefault(userId, Collections.emptyMap()).getOrDefault(conversationId, 0);
        return new ActiveConversation(
                conversationId,
                peer,
                meta != null ? meta.lastMessage : null,
                meta != null ? meta.updatedAt : null,
                unread
        );
    }

    private void linkConversationToUser(String userId, String conversationId) {
        conversationsByUser
                .computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(conversationId);
    }

    private void incrementUnread(String userId, String conversationId) {
        unreadMap.computeIfAbsent(userId, k -> new ConcurrentHashMap<>())
                .merge(conversationId, 1, Integer::sum);
    }

    private String resolvePeer(String userId, String conversationId) {
        List<ChatMessage> list = messagesByConversation.get(conversationId);
        if (list == null || list.isEmpty()) return null;
        ChatMessage last = list.get(list.size() - 1);
        if (userId.equals(last.getSenderId())) return last.getReceiverId();
        return last.getSenderId();
    }

    private static class ConversationMeta {
        private final String lastMessage;
        private final Instant updatedAt;
        ConversationMeta(String lastMessage, Instant updatedAt) {
            this.lastMessage = lastMessage;
            this.updatedAt = updatedAt;
        }
    }
}
