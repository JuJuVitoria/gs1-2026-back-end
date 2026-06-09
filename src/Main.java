import br.com.fiap.gs.model.ai.AISuggestion;
import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.model.climate.ClimateAlert;

import br.com.fiap.gs.repository.config.DataSeeder;
import br.com.fiap.gs.repository.impl.ai.ChatMessageRepository;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.repository.impl.user.ManagementNotebookRepository;
import br.com.fiap.gs.repository.impl.user.PropertyRepository;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.repository.impl.ai.ChatSessionRepository;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;
import br.com.fiap.gs.repository.impl.climate.ClimateAlertRepository;

import br.com.fiap.gs.service.impl.ClimateServiceImpl;
import br.com.fiap.gs.service.impl.FarmerServiceImpl;
import br.com.fiap.gs.service.impl.ManagementNotebookServiceImpl;
import br.com.fiap.gs.service.impl.PropertyServiceImpl;
import br.com.fiap.gs.service.impl.AgroIntelligenceServiceImpl;

import java.time.LocalDate;

import static br.com.fiap.gs.repository.config.DataSeeder.FARMER_1_PROPERTY_1;

public class Main {
    public static void main(String[] args) {

        FarmerRepository farmerRepository = new FarmerRepository();
        PropertyRepository propertyRepository = new PropertyRepository();
        AgroclimaticRepository agroclimaticRepository = new AgroclimaticRepository();
        AISuggestionRepository aiSuggestionRepository = new AISuggestionRepository();
        ManagementNotebookRepository managementNotebookRepository = new ManagementNotebookRepository();
        ClimateAlertRepository climateAlertRepository = new ClimateAlertRepository();
        ChatSessionRepository chatSessionRepository = new ChatSessionRepository();
        ChatMessageRepository chatMessageRepository = new ChatMessageRepository();

        FarmerServiceImpl farmerService = new FarmerServiceImpl(farmerRepository);
        PropertyServiceImpl propertyService = new PropertyServiceImpl(propertyRepository);
        ClimateServiceImpl climateService = new ClimateServiceImpl(agroclimaticRepository, climateAlertRepository);
        ManagementNotebookServiceImpl managementService = new ManagementNotebookServiceImpl(managementNotebookRepository);
        AgroIntelligenceServiceImpl agroService = new AgroIntelligenceServiceImpl(aiSuggestionRepository);

        new DataSeeder(farmerRepository, propertyRepository, agroclimaticRepository,
                aiSuggestionRepository, managementNotebookRepository, climateAlertRepository,
                chatSessionRepository, chatMessageRepository);

        farmerService.getListFarmer().forEach(f ->
                System.out.println("Farmer carregado: " + f.getName() + " | " + f.getEmail())
        );

        boolean auth = farmerService.authFarmer("madalena@astrocrop.com.br", "senha123");
        System.out.println("Login Madalena: " + auth);

        if (auth) {
            farmerService.setLoggedUser(
                    farmerService.searchByEmail("madalena@astrocrop.com.br")
            );
            System.out.println("Usuário logado: " + farmerService.getLoggedFarmer().getName());
        }

        System.out.println("Propriedades: " + propertyService.listPropertiesByProducer(
                farmerService.getLoggedFarmer().getId()));

        Agroclimatic forecast = new Agroclimatic(
                FARMER_1_PROPERTY_1, LocalDate.now(), 25, 2, 15.0, 45, 30.0
        );
        climateService.registerAgroclimatic(forecast);
        System.out.println("\nPrevisão registrada");

        ClimateAlert alert = climateService.generateClimateAlert(forecast);
        if (alert != null) {
            climateAlertRepository.save(alert);
            System.out.println("\nAlerta gerado: " + alert.getAlertType()
                    + " | Risco: " + alert.getSeverity());
        }

        climateService.getActiveAlerts(FARMER_1_PROPERTY_1)
                .ifPresentOrElse(
                        a -> System.out.println("\nAlerta ativo: " + a.getDescription()),
                        () -> System.out.println("Nenhum alerta ativo")
                );


        AISuggestion rec = agroService.recomendationsAI(
                FARMER_1_PROPERTY_1, "florescimento", "frio"
        );
        System.out.println("\nRecomendação IA: " + rec.getSuggestionText());

        String risco = agroService.evaluateHarvestRisk(FARMER_1_PROPERTY_1);
        System.out.println("Risco de colheita: " + risco);

        String janela = agroService.generatePlantingWindow(FARMER_1_PROPERTY_1);
        System.out.println("Janela de plantio: " + janela);

        System.out.println("\nHistórico de sugestões IA:");
        agroService.listAISuggestionHistory(FARMER_1_PROPERTY_1)
                .forEach(s -> System.out.println("   → [" + s.getSuggestionType()
                        + " | " + s.getDataGenerated() + "] " + s.getSuggestionText()));

        System.out.println("\nResumo IA: " +
                agroService.getRecommendationSummary(FARMER_1_PROPERTY_1));

        System.out.println("\nHistórico de previsões:");
        climateService.consultHistory(DataSeeder.FARMER_3_PROPERTY_1)
                .forEach(f -> System.out.println("   → " + f.getForecastDate()
                        + " | Chuva: " + f.getPrecipitation() + "mm"));

        System.out.println("\nAnotações: ");
        managementService.listNotebookByProperty(DataSeeder.FARMER_3_PROPERTY_1)
                .forEach(n -> System.out.println("   → " + n.getContent()
                        + " | Data da anotação: " + n.getRegistrationDate()));
    }
}