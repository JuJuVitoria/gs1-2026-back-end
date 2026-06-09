package br.com.fiap.gs.view;

import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.model.user.Property;
import br.com.fiap.gs.service.impl.*;
import br.com.fiap.gs.service.interfaces.ChatService;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    //
    private final FarmerServiceImpl farmerService;
    private final PropertyServiceImpl propertyService;
    private final AuthServiceImpl authService;
    private final ManagementMenu managementMenu;

    private final AgroclimaticMenu agroClimateMenu;

    public MainMenu(FarmerServiceImpl farmerService,
                    PropertyServiceImpl propertyService,
                    ManagementNotebookServiceImpl managementService,
                    AgroIntelligenceServiceImpl agroService,
                    ClimateServiceImpl climateService,
                    ChatService chatService,
                    AuthServiceImpl authService,
                    ManagementMenu managementMenu,
                    AgroclimaticMenu agroClimateMenu) {
        this.scanner         = new Scanner(System.in);
        this.farmerService   = farmerService;
        this.propertyService = propertyService;
        this.authService     = authService;

        this.managementMenu = managementMenu;
        this.agroClimateMenu = agroClimateMenu;
    }

    public void showMenu() {
        resolveActiveProperty();

        int option = -1;
        do {
            printHeader();
            System.out.print("Opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (option) {
                case 1 -> managementMenu.show();
                case 2 -> agroClimateMenu.show();
                case 3 -> System.out.println("\nEm desenvolvimento!\n");
                case 4 -> switchActiveProperty();
                case 0 -> System.out.println("\nSaindo...\n");
                default -> System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private void resolveActiveProperty() {
        Farmer farmer = authService.getLoggedUser();
        if (farmer == null) return;

        List<Property> properties = propertyService.listPropertiesByProducer(farmer.getId());

        if (properties.isEmpty()) {
            System.out.println("""
                
                Você ainda não possui nenhuma propriedade cadastrada.
                
                """);
            Property newProperty = registerProperty();
            propertyService.setActiveProperty(newProperty);
            System.out.println("Propriedade " + newProperty.getFarmName() + " cadastrada...");
            return;
        }

        if (properties.size() == 1) {
            Property only = properties.get(0);
            propertyService.setActiveProperty(only);
            System.out.printf("""
                
                Propriedade carregada automaticamente: %s
                """, only.getFarmName());
            return;
        }

        printPropertySelectionTable(properties);

        Property chosen = readPropertyChoice(properties);
        propertyService.setActiveProperty(chosen);
        System.out.printf("%nPropriedade selecionada: %s%n", chosen.getFarmName());
    }

    private void switchActiveProperty() {
        Farmer farmer = authService.getLoggedUser();
        if (farmer == null) return;

        List<Property> properties = propertyService.listPropertiesByProducer(farmer.getId());

        if (properties.isEmpty()) {
            System.out.println("""
                
                Você não possui nenhuma propriedade cadastrada.
                Acesse o menu "Propriedades" para adicionar uma.
                """);
            return;
        }

        if (properties.size() == 1) {
            System.out.printf("""
                
                    Você possui apenas uma propriedade cadastrada: %s
                    Não há outra para selecionar.
                """, properties.get(0).getFarmName());
            return;
        }

        Property current = propertyService.getActiveProperty();

        System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              Trocar propriedade ativa                               ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                                     ║");
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            boolean isActive = current != null && p.getId().equals(current.getId());
            String marker = isActive ? "+" : " ";
            System.out.printf("║        [%d] %s %-71s║%n", i + 1, marker, p.getFarmName());
        }
        System.out.println("║        [0] Para cadastrar uma nova propriedade                                      ║");
        System.out.println("║                                                                                     ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║       [-1] Cancelar                                                                 ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("  + = propriedade atualmente ativa");

        System.out.print("\nEscolha: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim());
            if (idx == -1) {
                System.out.println("\n  -Operação cancelada.\n");
                return;
            } else if (idx == 0) {
                Property newProperty = registerProperty();
                propertyService.setActiveProperty(newProperty);
                System.out.printf("%nPropriedade trocada para: %s%n%n", newProperty.getFarmName());
            } else if (idx >= 1 && idx <= properties.size()) {
                Property chosen = properties.get(idx - 1);

                if (current != null && chosen.getId().equals(current.getId())) {
                    System.out.printf("%n\"%s\" já é a propriedade ativa.%n%n", chosen.getFarmName());
                } else {
                    propertyService.setActiveProperty(chosen);
                    System.out.printf("%nPropriedade trocada para: %s%n%n", chosen.getFarmName());
                }
            } else {
                System.out.println("\nNúmero fora do intervalo. Nenhuma alteração feita.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada inválida. Nenhuma alteração feita.\n");
        }
    }

    private void printHeader() {
        Property active = propertyService.getActiveProperty();
        String propLabel = (active != null)
                ? active.getFarmName()
                : "Nenhuma propriedade selecionada";

        // Limita o label a 54 chars para caber na caixa
        if (propLabel.length() > 54) propLabel = propLabel.substring(0, 51) + "...";

        System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf( "║      %-79s║%n", propLabel);
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                                     ║");
        System.out.println("║       1. Caderno de Gestão                                                          ║");
        System.out.println("║       2. Agro / Clima                                                               ║");
        System.out.println("║       3. AI                                                                         ║");
        System.out.println("║       4. Trocar propriedade ativa                                                   ║");
        System.out.println("║       0. Sair                                                                       ║");
        System.out.println("║                                                                                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
    }

    private boolean requireActiveProperty() {
        if (propertyService.getActiveProperty() != null) return true;

        System.out.println("\n! Nenhuma propriedade selecionada.");
        System.out.print("Deseja selecionar uma agora? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (resp.equals("s")) {
            resolveActiveProperty();
            return propertyService.getActiveProperty() != null;
        }
        return false;
    }

    private void printPropertySelectionTable(List<Property> properties) {
        System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       Selecione a propriedade para esta sessão                      ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                                     ║");
        for (int i = 0; i < properties.size(); i++) {
            System.out.printf("║        [%d] %-73s║%n", i + 1, properties.get(i).getFarmName());
        }
        System.out.println("║        [0] Para cadastrar uma nova propriedade                                      ║");
        System.out.println("║                                                                                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
    }

    private Property readPropertyChoice(List<Property> properties) {
        while (true) {
            System.out.print("Escolha: ");

            try {
                int option = Integer.parseInt(scanner.nextLine().trim());

                if (option == 0) {
                    return registerProperty();
                }

                int idx = option - 1;

                if (idx >= 0 && idx < properties.size()) {
                    return properties.get(idx);
                }

                System.out.println("Número fora do intervalo. Tente novamente.");

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite o número da propriedade.");
            }
        }
    }

    private Property registerProperty() {
        System.out.println("""
    
    ┌─────────────────────────────────────────────────────────────────────────────────────┐
    │                             CADASTRE SUA PROPRIEDADE                                │
    └─────────────────────────────────────────────────────────────────────────────────────┘
    """);

        System.out.print("Digite o nome da sua propriedade: ");
        String farmName = scanner.nextLine().trim();

        double latitude;
        while (true) {
            try {
                System.out.print("Latitude: ");
                latitude = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Latitude inválida. Tente novamente.");
            }
        }

        double longitude;
        while (true) {
            try {
                System.out.print("Longitude: ");
                longitude = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Longitude inválida. Tente novamente.");
            }
        }

        Farmer farmer = authService.getLoggedUser();

        if (farmer == null) {
            throw new IllegalStateException("Nenhum usuário logado.");
        }

        return propertyService.registerProperty(
                farmer.getId(),
                farmName,
                latitude,
                longitude
        );
    }
}