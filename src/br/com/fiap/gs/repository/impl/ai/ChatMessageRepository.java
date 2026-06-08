package br.com.fiap.gs.repository.impl.ai;

import br.com.fiap.gs.enums.SenderType;
import br.com.fiap.gs.model.ai.ChatMessage;
import br.com.fiap.gs.repository.InMemoryRepository;

import java.util.List;
import java.util.UUID;

public class ChatMessageRepository extends InMemoryRepository<ChatMessage> {
    @Override
    protected UUID getIdOf(ChatMessage entity) {
        return entity.getId();
    }

    public List<ChatMessage> findAllBySessionId(UUID sessionId) {
        return store.values().stream()
                .filter(chat -> chat.getIdSession().equals(sessionId))
                .toList();
    }

    public List<ChatMessage> findAllBySessionIdAndSenderType(UUID sessionId, SenderType sender) {
        return store.values().stream()
                .filter(chat -> chat.getIdSession().equals(sessionId)
                        && chat.getSenderType() == sender)
                .toList();
    }
}
