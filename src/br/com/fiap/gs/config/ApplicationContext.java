package br.com.fiap.gs.config;

import br.com.fiap.gs.repository.config.DataSeeder;
import br.com.fiap.gs.repository.impl.ai.*;
import br.com.fiap.gs.repository.impl.climate.*;
import br.com.fiap.gs.repository.impl.user.*;

import br.com.fiap.gs.service.impl.*;
import br.com.fiap.gs.service.interfaces.ChatService;
import br.com.fiap.gs.view.LoginMenu;
import br.com.fiap.gs.view.MainMenu;

public class ApplicationContext {

    // Repositories
    private final FarmerRepository farmerRepository = new FarmerRepository();
    private final PropertyRepository propertyRepository = new PropertyRepository();
    private final AgroclimaticRepository agroclimaticRepository = new AgroclimaticRepository();
    private final AISuggestionRepository aiSuggestionRepository = new AISuggestionRepository();
    private final ManagementNotebookRepository managementNotebookRepository = new ManagementNotebookRepository();
    private final ClimateAlertRepository climateAlertRepository = new ClimateAlertRepository();
    private final ChatSessionRepository chatSessionRepository = new ChatSessionRepository();
    private final ChatMessageRepository chatMessageRepository = new ChatMessageRepository();

    // Services
    private final AuthServiceImpl authService =
            new AuthServiceImpl(farmerRepository);

    private final FarmerServiceImpl farmerService =
            new FarmerServiceImpl(farmerRepository);

    private final PropertyServiceImpl propertyService =
            new PropertyServiceImpl(propertyRepository);

    private final ClimateServiceImpl climateService =
            new ClimateServiceImpl(
                    agroclimaticRepository,
                    climateAlertRepository
            );

    private final ManagementNotebookServiceImpl managementService =
            new ManagementNotebookServiceImpl(
                    managementNotebookRepository
            );

    private final AgroIntelligenceServiceImpl agroService =
            new AgroIntelligenceServiceImpl(
                    aiSuggestionRepository
            );

    private final ChatService chatService =
            new ChatServiceImpl(
                    chatSessionRepository,
                    chatMessageRepository
            );

    private final MainMenu mainMenu =
            new MainMenu(
                    farmerService,
                    propertyService,
                    managementService,
                    agroService,
                    climateService,
                    chatService,
                    authService
            );

    private final LoginMenu loginMenu =
            new LoginMenu(authService, mainMenu);

    public void seedData() {
        new DataSeeder(
                farmerRepository,
                propertyRepository,
                agroclimaticRepository,
                aiSuggestionRepository,
                managementNotebookRepository,
                climateAlertRepository,
                chatSessionRepository,
                chatMessageRepository
        );
    }

    public LoginMenu getLoginMenu() {
        return loginMenu;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public FarmerServiceImpl getFarmerService() {
        return farmerService;
    }

    public void showUsersTest() {
        System.out.print("""
        ┌─────────────────────────────────────────────────────────────────────────────────────┐
        │                                 USUÁRIOS PARA TESTE                                 │
        ├──────────────────┬────────────────────────────┬─────────────────────────────────────┤
        │ PERFIL           │ LOGIN                      │ SENHA                               │
        ├──────────────────┼────────────────────────────┼─────────────────────────────────────┤
        """);

        farmerRepository.findAll().forEach(farmer ->
                System.out.printf(
                        "│ %-16s │ %-26s │ %-35s │%n",
                        "Produtor Rural",
                        farmer.getEmail(),
                        farmer.getPassword()
                )
        );

        System.out.print("""
        └──────────────────┴────────────────────────────┴─────────────────────────────────────┘

        ═══════════════════════════════════════════════════════════════════════════════════════
        """);
    }
}