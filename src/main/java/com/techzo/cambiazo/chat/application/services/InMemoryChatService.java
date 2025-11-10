package com.techzo.cambiazo.chat.application.services;

import com.techzo.cambiazo.chat.application.dtos.ActiveConversation;
import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import com.techzo.cambiazo.chat.application.dtos.ConversationSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "chat.store", havingValue = "inmem", matchIfMissing = true)
public class InMemoryChatService implements ChatService {

    private final Map<String, List<ChatMessage>> messagesByConversation = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> conversationsByUser = new ConcurrentHashMap<>();
    private final Map<String, ConversationMeta> conversationMeta = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Integer>> unreadMap = new ConcurrentHashMap<>();

    @Override
    public String ensureConversation(String conversationId) {
        String cid = (conversationId == null || conversationId.isBlank())
                ? UUID.randomUUID().toString()
                : conversationId;

        messagesByConversation.computeIfAbsent(cid, k -> Collections.synchronizedList(new ArrayList<>()));
        conversationMeta.putIfAbsent(cid, new ConversationMeta(null, null, "open", new Date(0)));
        return cid;
    }

    @Override
    public void addMessage(String conversationId, ChatMessage msg) {
        ConversationMeta meta = conversationMeta.get(conversationId);
        if (meta != null && "closed".equalsIgnoreCase(meta.status)) return;

        if (msg.getTimestamp() == null) msg.setTimestamp(new Date());

        messagesByConversation
                .computeIfAbsent(conversationId, k -> Collections.synchronizedList(new ArrayList<>()))
                .add(msg);

        linkConversationToUser(msg.getSenderId(), conversationId);
        linkConversationToUser(msg.getReceiverId(), conversationId);

        Date updatedAt = safeDate(msg.getTimestamp());

        String prevExchange = (meta != null) ? meta.exchangeId : null;
        String exchangeId = (msg.getExchangeId() != null && !msg.getExchangeId().isBlank())
                ? msg.getExchangeId()
                : prevExchange;

        conversationMeta.put(
                conversationId,
                new ConversationMeta(msg.getContent(), exchangeId, "open", updatedAt)
        );

        incrementUnread(msg.getReceiverId(), conversationId);
    }

    @Override
    public List<ChatMessage> getMessages(String conversationId) {
        return messagesByConversation.getOrDefault(conversationId, Collections.emptyList());
    }

    @Override
    public void clearChat(String conversationId) {
        messagesByConversation.remove(conversationId);
        conversationMeta.remove(conversationId);
        for (Map<String, Integer> m : unreadMap.values()) m.remove(conversationId);
        for (Set<String> s : conversationsByUser.values()) s.remove(conversationId);
    }

    @Override
    public List<ActiveConversation> getActiveConversations(String userId) {
        Set<String> convs = conversationsByUser.getOrDefault(userId, Collections.emptySet());
        List<ActiveConversation> out = new ArrayList<>(convs.size());
        for (String cid : convs) {
            out.add(getActiveConversationFor(userId, cid));
        }
        out.sort((a, b) -> {
            Date da = (a.getUpdatedAt() != null) ? a.getUpdatedAt() : new Date(0);
            Date db = (b.getUpdatedAt() != null) ? b.getUpdatedAt() : new Date(0);
            return db.compareTo(da);
        });
        return out;
    }

    @Override
    public void markConversationRead(String userId, String conversationId) {
        unreadMap.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(conversationId, 0);
    }

    @Override
    public ActiveConversation getActiveConversationFor(String userId, String conversationId) {
        ConversationMeta meta = conversationMeta.get(conversationId);
        String peer = resolvePeer(userId, conversationId);
        int unread = unreadMap
                .getOrDefault(userId, Collections.emptyMap())
                .getOrDefault(conversationId, 0);

        return new ActiveConversation(
                conversationId,
                peer,
                meta != null ? meta.lastMessage : null,
                meta != null ? meta.updatedAt : null,
                unread,
                meta != null ? meta.exchangeId : null
        );
    }

    @Override
    public List<String> getConversationIds(String userId) {
        return new ArrayList<>(conversationsByUser.getOrDefault(userId, Collections.emptySet()));
    }

    @Override
    public void closeConversation(String conversationId) {
        clearChat(conversationId);
    }

    @Override
    public String getConversationStatus(String conversationId) {
        ConversationMeta meta = conversationMeta.get(conversationId);
        return meta == null ? "closed" : meta.status;
    }

    @Override
    public List<ConversationSummary> getAllConversationSummaries() {
        return conversationMeta.entrySet().stream()
                .sorted((a, b) -> {
                    Date da = (a.getValue().updatedAt != null) ? a.getValue().updatedAt : new Date(0);
                    Date db = (b.getValue().updatedAt != null) ? b.getValue().updatedAt : new Date(0);
                    return db.compareTo(da);
                })
                .map(e -> {
                    String cid = e.getKey();
                    List<ChatMessage> msgs = messagesByConversation.getOrDefault(cid, Collections.emptyList());
                    String sender = null, receiver = null;
                    if (!msgs.isEmpty()) {
                        ChatMessage first = msgs.get(0);
                        sender = first.getSenderId();
                        receiver = first.getReceiverId();
                    }
                    String ex = e.getValue() != null ? e.getValue().exchangeId : null;
                    return new ConversationSummary(cid, sender, receiver, ex);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void setConversationExchangeId(String conversationId, String exchangeId) {
        ConversationMeta prev = conversationMeta.get(conversationId);
        if (prev == null) {
            conversationMeta.put(conversationId, new ConversationMeta(null, exchangeId, "open", new Date(0)));
        } else {
            conversationMeta.put(conversationId, new ConversationMeta(prev.lastMessage, exchangeId, prev.status, prev.updatedAt));
        }
    }

    @Override
    public String getConversationExchangeId(String conversationId) { // ✅ agregado
        ConversationMeta meta = conversationMeta.get(conversationId);
        return (meta != null) ? meta.exchangeId : null;
    }

    // -------- helpers --------

    private void linkConversationToUser(String userId, String conversationId) {
        if (userId == null || userId.isBlank()) return;
        conversationsByUser
                .computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(conversationId);
    }

    private void incrementUnread(String userId, String conversationId) {
        if (userId == null || userId.isBlank()) return;
        unreadMap
                .computeIfAbsent(userId, k -> new ConcurrentHashMap<>())
                .merge(conversationId, 1, Integer::sum);
    }

    private String resolvePeer(String userId, String conversationId) {
        List<ChatMessage> list = messagesByConversation.get(conversationId);
        if (list == null || list.isEmpty()) return null;
        ChatMessage last = list.get(list.size() - 1);
        if (userId != null && userId.equals(last.getSenderId())) return last.getReceiverId();
        return last.getSenderId();
    }

    private static Date safeDate(Date d) {
        return (d == null) ? new Date() : d;
    }

    private static class ConversationMeta {
        private final String lastMessage;
        private final String exchangeId;
        private final String status;
        private final Date updatedAt;

        ConversationMeta(String lastMessage, String exchangeId, String status, Date updatedAt) {
            this.lastMessage = lastMessage;
            this.exchangeId = exchangeId;
            this.status = status;
            this.updatedAt = updatedAt;
        }
    }
}
