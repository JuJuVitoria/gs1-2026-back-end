package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.model.ai.AISuggestion;

import java.util.List;
import java.util.UUID;

public interface AgroIntelligenceService {
    AISuggestion recomendationsAI(UUID idProperty, String plantingPhase, String currentForecast);
    void generateAIQuery();
    List<AISuggestion> listAISuggestionHistory(UUID idProperty);
    String generatePlantingWindow(UUID idProperty);
    List<String> suggestCrops(String region, String forecast);
    String evaluateHarvestRisk(UUID idProperty);
    String generateAlertMessage(UUID idProperty);
    String getRecommendationSummary(UUID idProp);
    String getRiskLevel(UUID idProperty);
}
