package br.com.fiap.gs.view;

import br.com.fiap.gs.enums.ActivityType;
import br.com.fiap.gs.model.user.PlantationRecord;
import br.com.fiap.gs.service.impl.ManagementNotebookServiceImpl;
import br.com.fiap.gs.service.impl.PropertyServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ManagementMenu {

    private final ManagementNotebookServiceImpl notebookService;
    private final PropertyServiceImpl propertyService;
    private final Scanner scanner;

    public ManagementMenu(ManagementNotebookServiceImpl notebookService,
                          PropertyServiceImpl propertyService) {
        this.notebookService = notebookService;
        this.propertyService = propertyService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        int option = -1;
        do {
            System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                   Caderno de Gestão                                 ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                                     ║");
            System.out.println("║       1. Registrar Atividade                                                        ║");
            System.out.println("║       2. Listar Atividades                                                          ║");
            System.out.println("║       3. Excluir Atividade                                                          ║");
            System.out.println("║       0. Voltar                                                                     ║");
            System.out.println("║                                                                                     ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (option) {
                case 1 -> registerActivity();
                case 2 -> listActivities();
                case 3 -> deleteActivity();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    public void registerActivity() {
        System.out.println("\n--- Registrar Atividade ---");
        UUID idProperty = resolveActiveProperty();
        if (idProperty == null) return;

        System.out.println("Tipos de atividade disponíveis:");
        ActivityType[] types = ActivityType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, types[i]);
        }
        System.out.print("Escolha o tipo: ");
        ActivityType type;
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= types.length) {
                System.out.println("Tipo inválido.");
                return;
            }
            type = types[idx];
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        System.out.print("Descrição da atividade: ");
        String content = scanner.nextLine().trim();

        notebookService.registerManagementActivity(idProperty, type, content, LocalDateTime.now());
        System.out.println("Atividade registrada.");
    }

    public void listActivities() {
        System.out.println("\n--- Atividades da Propriedade ---");
        UUID idProperty = resolveActiveProperty();
        if (idProperty == null) return;

        List<PlantationRecord> records = notebookService.listNotebookByProperty(idProperty);
        if (records.isEmpty()) {
            System.out.println("Nenhuma atividade registrada.");
        } else {
            records.forEach(r ->
                    System.out.printf("  [%s] %s | %s | %s%n",
                            r.getId(), r.getActivityType(), r.getRegistrationDate(), r.getContent())
            );
        }
    }

    public void deleteActivity() {
        System.out.println("\n--- Excluir Atividade ---");
        System.out.print("ID da atividade: ");
        try {
            UUID id = UUID.fromString(scanner.nextLine().trim());
            notebookService.deleteActivity(id);
            System.out.println("Atividade removida.");
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private UUID resolveActiveProperty() {
        if (propertyService.getActiveProperty() != null) {
            UUID id = propertyService.getActiveProperty().getId();
            System.out.println("Propriedade ativa: " + propertyService.getActiveProperty().getFarmName());
            return id;
        }
        System.out.print("ID da propriedade: ");
        try {
            return UUID.fromString(scanner.nextLine().trim());
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido.");
            return null;
        }
    }
}
