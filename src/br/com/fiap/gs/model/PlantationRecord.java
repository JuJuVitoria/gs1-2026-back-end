package br.com.fiap.gs.model;

import br.com.fiap.gs.enums.ActivityType;

import java.time.LocalDateTime;

public class PlantationRecord {
    private long id;
    private long idProperty;
    //Depois mudar para o enum ActivityType
    private ActivityType activityType;
    private String content;
    private LocalDateTime registrationDate;

    public PlantationRecord(long idProperty, ActivityType activityType, String content, LocalDateTime registrationDate) {
        this.idProperty = idProperty;
        this.activityType = activityType;
        this.content = content;
        this.registrationDate = registrationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(long idProperty) {
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
