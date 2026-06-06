package br.com.fiap.gs.model;

import java.time.LocalDate;

public class AISuggestion {
    private long id;
    private long idProperty;
    private String content;
    private String suggestionText;
    private LocalDate dataGenerated;

    public AISuggestion(long idProperty, String suggestion, LocalDate dataGenerated) {
        this.idProperty = idProperty;
        this.suggestionText = suggestion;
        this.dataGenerated = dataGenerated;
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
