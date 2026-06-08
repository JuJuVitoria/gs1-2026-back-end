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
import br.com.fiap.gs.service.impl.PropertyServiceImpl;

import java.time.LocalDate;

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

        new DataSeeder(farmerRepository,
                propertyRepository,
                agroclimaticRepository,
                aiSuggestionRepository,
                managementNotebookRepository,
                climateAlertRepository,
                chatSessionRepository,
                chatMessageRepository);

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

        System.out.println("Propriedades: " + propertyService.listPropertiesByProducer(farmerService.getLoggedFarmer().getId()));

        Agroclimatic forecast = new Agroclimatic(
                DataSeeder.FARMER_1_PROPERTY_1,
                LocalDate.now(),
                25,
                2,
                15.0,
                45,
                30.0
        );

        climateService.registerAgroclimatic(forecast);
        System.out.println("\nPrevisão registrada");

        ClimateAlert alert = climateService.generateClimateAlert(forecast);
        if (alert != null) {
            climateAlertRepository.save(alert);
            System.out.println("\nAlerta gerado: " + alert.getAlertType()
                    + " | Risco: "         + alert.getSeverity());
        }

        climateService.getActiveAlerts(DataSeeder.FARMER_1_PROPERTY_1)
                .ifPresentOrElse(
                        a -> System.out.println("\nAlerta ativo: " + a.getDescription()),
                        () -> System.out.println("Nenhum alerta ativo")
                );

        System.out.println("\nHistórico de previsões:");
        climateService.consultHistory(DataSeeder.FARMER_1_PROPERTY_1)
                .forEach(f -> System.out.println("   → " + f.getForecastDate()
                        + " | Chuva: " + f.getPrecipitation() + "mm"));
    }
}