package br.com.fiap.gs.model;

import br.com.fiap.gs.enums.AlertType;
import br.com.fiap.gs.enums.RiskLevel;

import java.time.LocalDate;

public class ClimateAlert {
    private long id;
    private long idProperty;
    private String description;
    private RiskLevel severity;
    private AlertType alertType;
    private LocalDate generatedDate;

    public ClimateAlert(long idProperty, String description, RiskLevel severity, AlertType alertType, LocalDate generatedDate) {
        this.idProperty = idProperty;
        this.description = description;
        this.severity = severity;
        this.alertType = alertType;
        this.generatedDate = generatedDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RiskLevel getSeverity() {
        return severity;
    }

    public void setSeverity(RiskLevel severity) {
        this.severity = severity;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    @Override
    public String toString() {
        return this.description + " - " + this.severity + " - " + this.alertType;
    }
}
