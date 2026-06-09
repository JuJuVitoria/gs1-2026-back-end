package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.enums.SuggestionType;
import br.com.fiap.gs.model.ai.AISuggestion;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.service.interfaces.AgroIntelligenceService;

import java.time.LocalDate;
import java.util.*;

public class AgroIntelligenceServiceImpl implements AgroIntelligenceService {

    private final AISuggestionRepository suggestionRepository;

    private static final Map<String, List<String>> SUGGESTIONS_BY_PHASE = new HashMap<>();
    private static final Map<String, List<String>> CROPS_BY_FORECAST    = new HashMap<>();
    private static final Map<String, String>       PLANTING_WINDOWS      = new HashMap<>();

    static {
        SUGGESTIONS_BY_PHASE.put("germinacao", List.of(
                "Mantenha o solo úmido a 5 cm de profundidade. Irrigação leve 2x ao dia é recomendada.",
                "Evite compactação do solo durante a germinação. Use cobertura morta para reter umidade.",
                "Temperatura ideal entre 20 °C e 25 °C. Proteja as mudas de geadas noturnas."
        ));
        SUGGESTIONS_BY_PHASE.put("vegetativo", List.of(
                "Aplique nitrogênio em cobertura (40-60 kg/ha) para estimular o crescimento foliar.",
                "Monitore pragas foliares nesta fase. Inspecione as plantas 3x por semana.",
                "Deficiência de ferro detectada? Aplique quelato de ferro via foliar."
        ));
        SUGGESTIONS_BY_PHASE.put("florescimento", List.of(
                "Evite estresse hídrico. Mantenha irrigação regular durante o florescimento.",
                "Reduza o uso de inseticidas para preservar polinizadores nativos.",
                "Previsão de chuva forte? Considere cobertura temporária das flores mais sensíveis."
        ));
        SUGGESTIONS_BY_PHASE.put("frutificacao", List.of(
                "Aumente o fornecimento de potássio (K2O) para melhorar a qualidade dos frutos.",
                "Faça raleio dos frutos se a carga estiver acima de 80% da capacidade da planta.",
                "Monitore pH do solo (ideal 6.0-6.5) para maximizar absorção de nutrientes."
        ));
        SUGGESTIONS_BY_PHASE.put("colheita", List.of(
                "Realize a colheita nas primeiras horas da manhã para preservar a qualidade.",
                "Verifique o teor de umidade dos grãos antes da trilha (ideal 14%).",
                "Prepare os silos com antecedência: temperatura abaixo de 20 °C e umidade relativa < 70%."
        ));

        CROPS_BY_FORECAST.put("seco",      List.of("Sorgo", "Girassol", "Algodão", "Feijão-caupi", "Milheto"));
        CROPS_BY_FORECAST.put("chuvoso",   List.of("Arroz", "Cana-de-açúcar", "Banana", "Mandioca", "Abóbora"));
        CROPS_BY_FORECAST.put("temperado", List.of("Soja", "Milho", "Trigo", "Aveia", "Canola"));
        CROPS_BY_FORECAST.put("frio",      List.of("Aveia", "Trigo de inverno", "Cevada", "Alho", "Batata"));

        PLANTING_WINDOWS.put("norte",       "Outubro a Dezembro (chuvas iniciais) ou Abril a Junho (segunda safra).");
        PLANTING_WINDOWS.put("nordeste",    "Fevereiro a Abril (chuvas regulares no semi-árido).");
        PLANTING_WINDOWS.put("centrooeste","Outubro a Novembro para soja; Janeiro para milho safrinha.");
        PLANTING_WINDOWS.put("sudeste",    "Outubro a Dezembro para culturas de verão; Abril a Junho para culturas de inverno.");
        PLANTING_WINDOWS.put("sul",        "Setembro a Novembro para soja e milho; Maio a Julho para trigo e aveia.");
        PLANTING_WINDOWS.put("default",    "Consulte o zoneamento agroclimático do MAPA para sua região e cultura.");
    }


    public AgroIntelligenceServiceImpl(AISuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @Override
    public AISuggestion recomendationsAI(UUID idProperty, String plantingPhase, String currentForecast) {
        String phase    = normalise(plantingPhase);
        String forecast = normalise(currentForecast);

        String phaseText    = pickRandom(SUGGESTIONS_BY_PHASE.getOrDefault(phase,
                List.of("Mantenha o monitoramento contínuo da lavoura.")));
        String forecastNote = buildForecastNote(forecast);

        String content = String.format(
                "[IA Agro] Fase: %s | Clima: %s\n%s\nObs. climática: %s",
                plantingPhase, currentForecast, phaseText, forecastNote
        );

        AISuggestion suggestion = new AISuggestion(
                idProperty, content, SuggestionType.RECOMMENDATION, LocalDate.now()
        );
        // saveOrReplace: garante só 1 RECOMMENDATION ativa por propriedade
        return suggestionRepository.saveOrReplace(suggestion);
    }

    @Override
    public void generateAIQuery() {
        List<UUID> propertyIds = suggestionRepository.findAll()
                .stream()
                .map(AISuggestion::getIdProperty)
                .distinct()
                .toList();

        if (propertyIds.isEmpty()) {
            System.out.println("[AgroAI] Nenhuma propriedade cadastrada. Nada a processar.");
            return;
        }

        for (UUID id : propertyIds) {
            recomendationsAI(id, "vegetativo", "temperado");
            System.out.printf("[AgroAI] Sugestão gerada automaticamente para propriedade %s%n", id);
        }
    }

    @Override
    public List<AISuggestion> listAISuggestionHistory(UUID idProperty) {
        return suggestionRepository.findByPropertyId(idProperty);
    }

    @Override
    public String generatePlantingWindow(UUID idProperty) {
        return suggestionRepository
                .findLatestByPropertyIdAndType(idProperty, SuggestionType.PLANTING_WINDOW)
                .filter(s -> !s.getDataGenerated().isBefore(LocalDate.now().minusDays(30)))
                .map(s -> {
                    System.out.println("[AgroAI] Reutilizando janela de plantio recente.");
                    return s.getSuggestionText();
                })
                .orElseGet(() -> {
                    String regionKey = inferRegion(idProperty);
                    String window    = PLANTING_WINDOWS.get(regionKey);

                    AISuggestion suggestion = new AISuggestion(
                            idProperty,
                            "[IA Agro] Janela de plantio recomendada: " + window,
                            SuggestionType.PLANTING_WINDOW,
                            LocalDate.now()
                    );
                    suggestionRepository.saveOrReplace(suggestion);
                    return window;
                });
    }

    @Override
    public List<String> suggestCrops(String region, String forecast) {
        String forecastKey = normalise(forecast);
        List<String> crops = new ArrayList<>(
                CROPS_BY_FORECAST.getOrDefault(forecastKey, CROPS_BY_FORECAST.get("temperado"))
        );
        System.out.printf("[AgroAI] Culturas sugeridas para região '%s' / clima '%s': %s%n",
                region, forecast, crops);
        return crops;
    }

    @Override
    public String evaluateHarvestRisk(UUID idProperty) {
        List<AISuggestion> alerts = suggestionRepository
                .findByPropertyIdAndType(idProperty, SuggestionType.ALERT);

        String risk;
        String detail;
        if (alerts.isEmpty()) {
            risk   = "BAIXO";
            detail = "Nenhum alerta registrado. Condições favoráveis para colheita.";
        } else if (alerts.size() <= 2) {
            risk   = "MÉDIO";
            detail = String.format("%d alerta(s) recente(s). Monitore as condições antes de iniciar.", alerts.size());
        } else {
            risk   = "ALTO";
            detail = String.format("%d alertas detectados. Recomenda-se aguardar estabilização climática.", alerts.size());
        }

        String result = String.format("[IA Agro] Risco de colheita: %s — %s", risk, detail);

        suggestionRepository.saveOrReplace(new AISuggestion(
                idProperty, result, SuggestionType.HARVEST_RISK, LocalDate.now()
        ));
        return result;
    }

    @Override
    public String generateAlertMessage(UUID idProperty) {
        List<String> possibleAlerts = List.of(
                "⚠️ Previsão de geada nas próximas 48h. Proteja mudas sensíveis.",
                "⚠️ Déficit hídrico detectado. Verifique sistema de irrigação.",
                "⚠️ Alta umidade relativa (>85%). Risco elevado de doenças fúngicas.",
                "⚠️ Temperatura acima de 35 °C prevista. Planeje irrigação noturna.",
                "⚠️ Ventos fortes (>60 km/h) previstos. Reforce tutores e estacas."
        );

        String alertText = pickRandom(possibleAlerts);

        suggestionRepository.save(new AISuggestion(
                idProperty, alertText, SuggestionType.ALERT, LocalDate.now()
        ));
        return alertText;
    }

    @Override
    public String getRecommendationSummary(UUID idProp) {
        List<AISuggestion> history = suggestionRepository.findByPropertyId(idProp);
        if (history.isEmpty()) {
            return "[IA Agro] Nenhuma sugestão encontrada para esta propriedade.";
        }

        long totalRec    = suggestionRepository.findByPropertyIdAndType(idProp, SuggestionType.RECOMMENDATION).size();
        long totalAlerts = suggestionRepository.findByPropertyIdAndType(idProp, SuggestionType.ALERT).size();

        String latestText = suggestionRepository
                .findLatestByPropertyIdAndType(idProp, SuggestionType.RECOMMENDATION)
                .map(AISuggestion::getSuggestionText)
                .orElse("—");

        LocalDate latestDate = suggestionRepository
                .findLatestByPropertyIdAndType(idProp, SuggestionType.RECOMMENDATION)
                .map(AISuggestion::getDataGenerated)
                .orElse(LocalDate.now());

        return String.format(
                "[IA Agro] Resumo da propriedade %s: %d recomendação(ões), %d alerta(s). " +
                        "Última recomendação em %s: \"%s\"",
                idProp, totalRec, totalAlerts, latestDate, latestText
        );
    }

    @Override
    public String getRiskLevel(UUID idProperty) {
        long recentAlerts = suggestionRepository
                .findByPropertyIdAndType(idProperty, SuggestionType.ALERT)
                .stream()
                .filter(s -> !s.getDataGenerated().isBefore(LocalDate.now().minusDays(7)))
                .count();

        if (recentAlerts == 0)  return "BAIXO";
        if (recentAlerts <= 2)  return "MÉDIO";
        return "ALTO";
    }

    private String inferRegion(UUID idProperty) {
        return suggestionRepository.findByPropertyId(idProperty)
                .stream()
                .map(s -> s.getSuggestionText().toLowerCase())
                .filter(t -> PLANTING_WINDOWS.keySet().stream().anyMatch(t::contains))
                .findFirst()
                .flatMap(t -> PLANTING_WINDOWS.keySet().stream().filter(t::contains).findFirst())
                .orElse("default");
    }

    private String normalise(String value) {
        if (value == null) return "default";
        return value.toLowerCase()
                .replace("ç","c").replace("ã","a").replace("á","a")
                .replace("â","a").replace("é","e").replace("ê","e")
                .replace("í","i").replace("ó","o").replace("ô","o")
                .replace("ú","u").replace("ü","u").replace(" ","")
                .trim();
    }

    private <T> T pickRandom(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    private String buildForecastNote(String forecast) {
        return switch (forecast) {
            case "seco"      -> "Chuva abaixo de 50 mm/mês esperada. Irrigação suplementar necessária.";
            case "chuvoso"   -> "Chuva acima de 150 mm/mês prevista. Atenção à drenagem e doenças foliares.";
            case "frio"      -> "Temperaturas mínimas abaixo de 10 °C. Risco de geada nos próximos 15 dias.";
            case "temperado" -> "Condições climáticas estáveis. Acompanhe o boletim agrometeorológico semanal.";
            default          -> "Clima indefinido. Consulte a previsão regional atualizada.";
        };
    }
}