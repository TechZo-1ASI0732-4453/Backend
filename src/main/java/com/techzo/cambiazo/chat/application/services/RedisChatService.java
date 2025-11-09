package com.techzo.cambiazo.chat.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techzo.cambiazo.chat.application.dtos.ActiveConversation;
import com.techzo.cambiazo.chat.application.dtos.ChatMessage;
import com.techzo.cambiazo.chat.application.dtos.ConversationSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@ConditionalOnProperty(name = "chat.store", havingValue = "redis")
public class RedisChatService implements ChatService {

    private final StringRedisTemplate srt;
    private final RedisTemplate<String, Object> rt;
    private final RedisTemplate<String, ChatMessage> chatRt;
    private final ObjectMapper mapper;

    public RedisChatService(StringRedisTemplate srt,
                            RedisTemplate<String, Object> rt,
                            RedisTemplate<String, ChatMessage> chatRt,
                            ObjectMapper mapper) {
        this.srt = srt;
        this.rt = rt;
        this.chatRt = chatRt;
        this.mapper = mapper;
    }

    private static String kConvMsgs(String cid)    { return "conv:" + cid + ":messages"; }
    private static String kConvMeta(String cid)    { return "conv:" + cid + ":meta"; }
    private static String kUserConvs(String uid)   { return "user:" + uid + ":convs"; }
    private static String kUnread(String uid)      { return "unread:" + uid; }
    private static String kConvUsers(String cid)   { return "conv:" + cid + ":users"; }
    private static final String K_ALL_CONVS        = "conv:all";
    private static final String K_CONV_UPDATED_ZS  = "conv:updated";

    @Override
    public String ensureConversation(String conversationId) {
        String cid = (conversationId == null || conversationId.isBlank())
                ? UUID.randomUUID().toString()
                : conversationId;

        BoundHashOperations<String, Object, Object> meta = rt.boundHashOps(kConvMeta(cid));
        if (Boolean.FALSE.equals(srt.hasKey(kConvMeta(cid)))) {
            meta.put("status", "open");
            meta.put("updatedAt", Instant.EPOCH.toString());
        }
        srt.opsForSet().add(K_ALL_CONVS, cid);
        srt.opsForZSet().add(K_CONV_UPDATED_ZS, cid, Instant.EPOCH.toEpochMilli());
        return cid;
    }

    @Override
    public void addMessage(String conversationId, ChatMessage msg) {
        BoundHashOperations<String, Object, Object> meta = rt.boundHashOps(kConvMeta(conversationId));
        Object status = meta.get("status");
        if (status != null && "closed".equalsIgnoreCase(String.valueOf(status))) return;

        if (msg.getId() == null || msg.getId().isBlank()) msg.setId(UUID.randomUUID().toString());

        chatRt.opsForList().rightPush(kConvMsgs(conversationId), msg);

        srt.opsForSet().add(K_ALL_CONVS, conversationId);
        if (msg.getSenderId() != null && !msg.getSenderId().isBlank())
            srt.opsForSet().add(kUserConvs(msg.getSenderId()), conversationId);
        if (msg.getReceiverId() != null && !msg.getReceiverId().isBlank())
            srt.opsForSet().add(kUserConvs(msg.getReceiverId()), conversationId);

        if (msg.getSenderId() != null)   srt.opsForSet().add(kConvUsers(conversationId), msg.getSenderId());
        if (msg.getReceiverId() != null) srt.opsForSet().add(kConvUsers(conversationId), msg.getReceiverId());

        String lastPreview =
                (msg.getType() == ChatMessage.MessageType.LOCATION)
                        ? ("📍 " + (
                        msg.getLocationLabel() != null && !msg.getLocationLabel().isBlank()
                                ? msg.getLocationLabel()
                                : (msg.getLatitude() + "," + msg.getLongitude())
                ))
                        : Optional.ofNullable(msg.getContent()).orElse("");

        meta.put("lastMessage", lastPreview);
        if (msg.getExchangeId() != null && !msg.getExchangeId().isBlank())
            meta.put("exchangeId", msg.getExchangeId());

        meta.put("lastType", msg.getType() != null ? msg.getType().name() : "TEXT");
        if (msg.getType() == ChatMessage.MessageType.LOCATION) {
            if (msg.getLatitude() != null)  meta.put("lastLat", String.valueOf(msg.getLatitude()));
            if (msg.getLongitude() != null) meta.put("lastLng", String.valueOf(msg.getLongitude()));
            if (msg.getLocationLabel() != null && !msg.getLocationLabel().isBlank())
                meta.put("lastLabel", msg.getLocationLabel());
        }

        meta.put("status", "open");

        String ts = Optional.ofNullable(msg.getTimestamp()).orElse(Instant.now().toString());
        meta.put("updatedAt", ts);

        meta.putIfAbsent("firstSenderId",   msg.getSenderId());
        meta.putIfAbsent("firstReceiverId", msg.getReceiverId());

        if (msg.getSenderId() != null)   meta.put("lastSenderId", msg.getSenderId());
        if (msg.getReceiverId() != null) meta.put("lastReceiverId", msg.getReceiverId());

        if (msg.getReceiverId() != null && !msg.getReceiverId().isBlank()) {
            Object curr = rt.opsForHash().get(kUnread(msg.getReceiverId()), conversationId);
            int next = (curr instanceof Number) ? ((Number) curr).intValue() + 1 : 1;
            rt.opsForHash().put(kUnread(msg.getReceiverId()), conversationId, next);
        }

        long score = Instant.parse(ts).toEpochMilli();
        srt.opsForZSet().add(K_CONV_UPDATED_ZS, conversationId, score);
    }

    @Override
    public List<ChatMessage> getMessages(String conversationId) {
        List<ChatMessage> list = chatRt.opsForList().range(kConvMsgs(conversationId), 0, -1);
        return list == null ? List.of() : list;
    }

    @Override
    public void clearChat(String conversationId) {
        Set<String> users = srt.opsForSet().members(kConvUsers(conversationId));
        if (users != null) {
            for (String u : users) {
                srt.opsForSet().remove(kUserConvs(u), conversationId);
                rt.opsForHash().delete(kUnread(u), conversationId);
            }
        }
        srt.delete(kConvUsers(conversationId));

        srt.delete(kConvMsgs(conversationId));
        srt.delete(kConvMeta(conversationId));
        srt.opsForSet().remove(K_ALL_CONVS, conversationId);
        srt.opsForZSet().remove(K_CONV_UPDATED_ZS, conversationId);
    }

    @Override
    public List<ActiveConversation> getActiveConversations(String userId) {
        Set<String> cids = srt.opsForSet().members(kUserConvs(userId));
        if (cids == null || cids.isEmpty()) return List.of();

        List<Object> batched = rt.executePipelined((RedisCallback<Object>) connection -> {
            for (String cid : cids) {
                connection.hGetAll(kConvMeta(cid).getBytes());
                connection.hGet(kUnread(userId).getBytes(), cid.getBytes());
            }
            return null;
        });

        List<ActiveConversation> out = new ArrayList<>(cids.size());
        Iterator<String> it = cids.iterator();
        for (int i = 0; i < batched.size(); i += 2) {
            String cid = it.next();

            @SuppressWarnings("unchecked")
            Map<byte[], byte[]> metaRaw = (Map<byte[], byte[]>) batched.get(i);
            Object unreadObj = batched.get(i + 1);

            String last = bytes(metaRaw, "lastMessage", "");
            String updatedStr = bytes(metaRaw, "updatedAt", null);
            String exchangeId = bytes(metaRaw, "exchangeId", null);

            String lastSender = bytes(metaRaw, "lastSenderId", null);
            String lastReceiver = bytes(metaRaw, "lastReceiverId", null);

            Instant updated = parseInstant(updatedStr);
            int unread = 0;
            if (unreadObj instanceof byte[] b) {
                try { unread = Integer.parseInt(new String(b)); } catch (Exception ignored) {}
            }

            String peer = null;
            if (userId != null) {
                if (userId.equals(lastSender)) peer = lastReceiver;
                else if (userId.equals(lastReceiver)) peer = lastSender;
            }

            out.add(new ActiveConversation(cid, peer, last, updated, unread, exchangeId));
        }

        out.sort(Comparator.comparing(ActiveConversation::getUpdatedAt, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
        return out;
    }

    @Override
    public void markConversationRead(String userId, String conversationId) {
        rt.opsForHash().put(kUnread(userId), conversationId, 0);
    }

    @Override
    public ActiveConversation getActiveConversationFor(String userId, String conversationId) {
        Map<Object, Object> meta = rt.opsForHash().entries(kConvMeta(conversationId));
        String last = str(meta.get("lastMessage"), "");
        String updatedStr = str(meta.get("updatedAt"), null);
        String exchangeId = str(meta.get("exchangeId"), null);
        String lastSender = str(meta.get("lastSenderId"), null);
        String lastReceiver = str(meta.get("lastReceiverId"), null);
        Instant updated = parseInstant(updatedStr);

        Object unreadObj = rt.opsForHash().get(kUnread(userId), conversationId);
        int unread = (unreadObj instanceof Number) ? ((Number) unreadObj).intValue() : 0;

        String peer = null;
        if (userId != null) {
            if (userId.equals(lastSender)) peer = lastReceiver;
            else if (userId.equals(lastReceiver)) peer = lastSender;
        }

        return new ActiveConversation(conversationId, peer, last, updated, unread, exchangeId);
    }

    @Override
    public List<String> getConversationIds(String userId) {
        Set<String> s = srt.opsForSet().members(kUserConvs(userId));
        return s == null ? List.of() : new ArrayList<>(s);
    }

    @Override
    public void closeConversation(String conversationId) {
        rt.boundHashOps(kConvMeta(conversationId)).put("status", "closed");
        clearChat(conversationId);
    }

    @Override
    public String getConversationStatus(String conversationId) {
        Object v = rt.boundHashOps(kConvMeta(conversationId)).get("status");
        return v == null ? "closed" : String.valueOf(v);
    }

    @Override
    public List<ConversationSummary> getAllConversationSummaries() {
        // usa ZSET para ordenar por updatedAt (más reciente primero)
        Set<String> cids = srt.opsForZSet().reverseRange(K_CONV_UPDATED_ZS, 0, -1);
        if (cids == null || cids.isEmpty()) return List.of();

        List<ConversationSummary> list = new ArrayList<>(cids.size());
        for (String cid : cids) {
            Map<Object, Object> meta = rt.opsForHash().entries(kConvMeta(cid));
            String firstSender   = str(meta.get("firstSenderId"), null);
            String firstReceiver = str(meta.get("firstReceiverId"), null);
            String exchangeId    = str(meta.get("exchangeId"), null);
            list.add(new ConversationSummary(cid, firstSender, firstReceiver, exchangeId));
        }
        return list;
    }

    @Override
    public void setConversationExchangeId(String conversationId, String exchangeId) {
        if (exchangeId == null || exchangeId.isBlank()) return;
        rt.boundHashOps(kConvMeta(conversationId)).put("exchangeId", exchangeId);
    }

    private static Instant parseInstant(String v) {
        try { return v == null ? null : Instant.parse(v); }
        catch (Exception e) { return null; }
    }

    private static String str(Object v, String def) {
        return v == null ? def : String.valueOf(v);
    }

    private static String bytes(Map<byte[], byte[]> m, String key, String def) {
        if (m == null) return def;
        for (var e : m.entrySet()) {
            if (new String(e.getKey()).equals(key)) {
                return e.getValue() == null ? def : new String(e.getValue());
            }
        }
        return def;
    }
}
