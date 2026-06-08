package br.com.fiap.gs.model.ai;

import java.time.LocalDate;
import java.util.UUID;

public class ChatSession {
    private UUID id;
    private UUID idFarmer;
    private String topic;
    private LocalDate startDate;

    public ChatSession(UUID idFarmer, String topic, LocalDate startDate) {
        this.id = UUID.randomUUID();
        this.idFarmer = idFarmer;
        this.topic = topic;
        this.startDate = startDate;
    }

    public ChatSession(UUID id, UUID idFarmer, String topic, LocalDate startDate) {
        this.id = id;
        this.idFarmer = idFarmer;
        this.topic = topic;
        this.startDate = startDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdFarmer() {
        return idFarmer;
    }

    public void setIdFarmer(UUID idFarmer) {
        this.idFarmer = idFarmer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return this.topic + " - " + this.startDate;
    }
}
