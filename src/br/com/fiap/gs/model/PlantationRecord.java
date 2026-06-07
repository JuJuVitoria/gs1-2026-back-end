package br.com.fiap.gs.model;

import br.com.fiap.gs.enums.ActivityType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlantationRecord {
    private UUID id;
    private UUID idProperty;
    private ActivityType activityType;
    private String content;
    private LocalDateTime registrationDate;

    public PlantationRecord(UUID idProperty, ActivityType activityType, String content, LocalDateTime registrationDate) {
        this.id = UUID.randomUUID();
        this.idProperty = idProperty;
        this.activityType = activityType;
        this.content = content;
        this.registrationDate = registrationDate;
    }

    public PlantationRecord(UUID id, UUID idProperty, ActivityType activityType, String content, LocalDateTime registrationDate) {
        this.id = id;
        this.idProperty = idProperty;
        this.activityType = activityType;
        this.content = content;
        this.registrationDate = registrationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(UUID idProperty) {
        this.idProperty = idProperty;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return this.content + " - " + this.registrationDate;
    }
}
