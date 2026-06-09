package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.enums.SenderType;
import br.com.fiap.gs.model.ai.ChatMessage;
import br.com.fiap.gs.model.ai.ChatSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    // --- Sessões ---
    ChatSession createSession(UUID idFarmer, String topic);
    List<ChatSession> listSessionsByFarmer(UUID idFarmer);
    Optional<ChatSession> findSessionById(UUID sessionId);
    List<ChatSession> searchSessionsByTopic(String topic);
    void deleteSession(UUID sessionId);

    // --- Mensagens ---
    ChatMessage sendMessage(UUID sessionId, SenderType senderType, String content);
    List<ChatMessage> listMessages(UUID sessionId);
    List<ChatMessage> listMessagesBySender(UUID sessionId, SenderType senderType);

    // --- Conversa completa ---
    void printConversation(UUID sessionId);
    String getConversationSummary(UUID sessionId);
}