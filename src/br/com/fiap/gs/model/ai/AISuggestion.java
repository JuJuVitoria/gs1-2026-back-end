package br.com.fiap.gs.model.ai;

import br.com.fiap.gs.enums.SuggestionType;

import java.time.LocalDate;
import java.util.UUID;

public class AISuggestion {
    private UUID id;
    private UUID idProperty;
    private String content;
    private String suggestionText;
    private SuggestionType suggestionType;
    private LocalDate dataGenerated;

    public AISuggestion(UUID idProperty, String suggestion, SuggestionType suggestionType, LocalDate dataGenerated) {
        this.id = UUID.randomUUID();
        this.idProperty = idProperty;
        this.suggestionText = suggestion;
        this.suggestionType = suggestionType;
        this.dataGenerated = dataGenerated;
    }

    public AISuggestion(UUID id, UUID idProperty, String suggestion, SuggestionType suggestionType, LocalDate dataGenerated) {
        this.id = id;
        this.idProperty = idProperty;
        this.suggestionText = suggestion;
        this.suggestionType = suggestionType;
        this.dataGenerated = dataGenerated;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuggestionText() {
        return suggestionText;
    }

    public void setSuggestionText(String suggestionText) {
        this.suggestionText = suggestionText;
    }

    public SuggestionType getSuggestionType() { return suggestionType; }

    public void setSuggestionType(SuggestionType suggestionType) { this.suggestionType = suggestionType; }

    public LocalDate getDataGenerated() {
        return dataGenerated;
    }

    public void setDataGenerated(LocalDate dataGenerated) {
        this.dataGenerated = dataGenerated;
    }

    @Override
    public String toString() {
        return this.suggestionText + " - " + this.dataGenerated;
    }
}
