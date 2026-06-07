package br.com.fiap.gs.model.ai;

import java.time.LocalDate;

public class ChatSession {
    private long id;
    private long idFarmer;
    private String topic;
    private LocalDate startDate;

    public ChatSession(long idFarmer, String topic, LocalDate startDate) {
        this.idFarmer = idFarmer;
        this.topic = topic;
        this.startDate = startDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdFarmer() {
        return idFarmer;
    }

    public void setIdFarmer(long idFarmer) {
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
