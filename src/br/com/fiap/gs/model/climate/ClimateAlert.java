package br.com.fiap.gs.model.climate;

import br.com.fiap.gs.enums.AlertType;
import br.com.fiap.gs.enums.RiskLevel;

import java.time.LocalDate;
import java.util.UUID;

public class ClimateAlert {
    private UUID id;
    private UUID idProperty;
    private String description;
    private RiskLevel severity;
    private AlertType alertType;
    private LocalDate generatedDate;
    private String status;

    public ClimateAlert(UUID idProperty, String description,
                        RiskLevel severity, AlertType alertType,
                        LocalDate generatedDate, String status) {
        this.id = UUID.randomUUID();
        this.idProperty = idProperty;
        this.description = description;
        this.severity = severity;
        this.alertType = alertType;
        this.generatedDate = generatedDate;
        this.status = status;
    }

    public ClimateAlert(UUID id, UUID idProperty,
                        String description, RiskLevel severity,
                        AlertType alertType, LocalDate generatedDate,
                        String status) {
        this.id = id;
        this.idProperty = idProperty;
        this.description = description;
        this.severity = severity;
        this.alertType = alertType;
        this.generatedDate = generatedDate;
        this.status = status;
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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return this.description + " - " + this.severity + " - " + this.alertType;
    }
}
