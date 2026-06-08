package br.com.fiap.gs.repository.impl.ai;

import br.com.fiap.gs.model.ai.ChatSession;
import br.com.fiap.gs.repository.impl.InMemoryRepository;

import java.util.List;
import java.util.UUID;

public class ChatSessionRepository extends InMemoryRepository<ChatSession> {

    @Override
    protected UUID getIdOf(ChatSession session) {
        return session.getId();
    }

    public List<ChatSession> findAllByFarmerId(UUID farmerId) {
        return store.values().stream()
                .filter(s -> s.getIdFarmer().equals(farmerId))
                .toList();
    }

    public List<ChatSession> findByTopic(String topic) {
        return store.values().stream()
                .filter(s -> s.getTopic().equalsIgnoreCase(topic))
                .toList();
    }
}