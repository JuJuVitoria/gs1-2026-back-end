package br.com.fiap.gs.view;

import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.model.climate.ClimateAlert;
import br.com.fiap.gs.service.impl.AgroIntelligenceServiceImpl;
import br.com.fiap.gs.service.impl.ClimateServiceImpl;
import br.com.fiap.gs.service.impl.PropertyServiceImpl;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class AgroclimaticMenu {

    private final ClimateServiceImpl agroclimaticService;
    private final AgroIntelligenceServiceImpl agroAIService;
    private final PropertyServiceImpl propertyService;
    private final Scanner scanner;

    public AgroclimaticMenu(ClimateServiceImpl agroclimaticService,
                            AgroIntelligenceServiceImpl agroAIService,
                            PropertyServiceImpl propertyService) {
        this.agroclimaticService = agroclimaticService;
        this.agroAIService       = agroAIService;
        this.propertyService     = propertyService;
        this.scanner = new Scanner(System.in);
    }

    public static final String SOL =
                    "      \\  |  /      \n" +
                    "     `. ☀ .'       \n" +
                    "   ― (     ) ―    \n" +
                    "     ,'._,'.       \n" +
                    "       / | \\        ";

    public static final String SOL_E_NUVEM =
                    "   \\  /         \n" +
                    " _ /\"\"\\ _     \n" +
                    "   \\_../        \n" +
                    "   _.(   )._     \n" +
                    "  (_________)      ";


    public static final String CHUVA =
                        "     .(   )._    \n" +
                        "    (_________)  \n" +
                        "     ` ` ` `     \n" +
                        "    ` ` ` `      \n";

    public static final String NUBLADO =
                        "     .(   )._    \n" +
                        "    (_________)  \n" +
                        "   (___________) \n" +
                        "                 \n";


    public void show() {
        int option = -1;
        do {
            printClimateHeader();
            System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                               Monitoramento Climático                               ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                                     ║");
            System.out.println("║       1. Gerar Recomendação                                                         ║");
            System.out.println("║       2. Ver Alertas Climáticos                                                     ║");
            System.out.println("║       3. Histórico de Previsões                                                     ║");
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
                case 1 -> generateRecommendation();
                case 2 -> showAlerts();
                case 3 -> showHistory();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private void printClimateHeader() {

        Agroclimatic forecast = getLatestForecast();

        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════╗");

        if (forecast == null) {

            System.out.printf(
                    "║ %-83s ║%n",
                    "Nenhuma previsão registrada para esta propriedade"
            );

        } else {

            String resumo = String.format(
                    "Hoje | %d°C/%d°C | %.1f mm | %d%% umidade | %.1f km/h",
                    forecast.getMinTemperature(),
                    forecast.getMaxTemperature(),
                    forecast.getPrecipitation(),
                    forecast.getAirHumidityPercentage(),
                    forecast.getWindSpeedKMH()
            );

            System.out.println("║                                                                                     ║");
            String[] art = getClimateArt(forecast).split("\n");

            for (String linha : art) {
                System.out.printf("║ %-83s ║%n", linha);
            }

            System.out.printf("║ %-83s ║%n", resumo);
            System.out.println("║                                                                                     ║");
        }
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
    }

    private Agroclimatic getLatestForecast() {

        if (propertyService.getActiveProperty() == null) {
            return null;
        }

        var history = agroclimaticService.consultHistory(
                propertyService.getActiveProperty().getId()
        );

        if (history.isEmpty()) {
            return null;
        }

        return history.get(history.size() - 1);
    }

    public void generateRecommendation() {
        System.out.println("\n--- Registrar Previsão e Gerar Recomendação ---");
        UUID idProperty = resolveActiveProperty();
        if (idProperty == null) return;

        double maxTemp  = readDouble("Temperatura máxima (°C): ");
        double minTemp  = readDouble("Temperatura mínima (°C): ");
        double precip   = readDouble("Precipitação prevista (mm): ");
        double humidity = readDouble("Umidade do ar (%): ");
        double wind     = readDouble("Velocidade do vento (km/h): ");

        Agroclimatic forecast = new Agroclimatic(
                idProperty, LocalDate.now(),
                (int) maxTemp, (int) minTemp, precip, (int) humidity, wind
        );
        agroclimaticService.registerAgroclimatic(forecast);

        ClimateAlert alert = agroclimaticService.generateClimateAlert(forecast);
        if (alert != null) {
            System.out.println("\nAlerta gerado: " + alert.getAlertType()
                    + " | Risco: " + alert.getSeverity()
                    + "\n   " + alert.getDescription());
        } else {
            System.out.println("\nNenhum alerta crítico detectado para os dados informados.");
        }

        String climaKey = resolveClimaKey(precip, minTemp);
        System.out.println("\nCulturas sugeridas para este clima:");
        agroAIService.suggestCrops("—", climaKey)
                .forEach(c -> System.out.println("   • " + c));
    }

    public void showAlerts() {
        System.out.println("\n--- Alertas Climáticos ---");
        UUID idProperty = resolveActiveProperty();
        if (idProperty == null) return;

        agroclimaticService.getActiveAlerts(idProperty).ifPresentOrElse(
                a -> System.out.println("Alerta ativo: [" + a.getAlertType()
                        + " | " + a.getSeverity() + "] " + a.getDescription()),
                () -> System.out.println("Nenhum alerta ativo no momento.")
        );

        System.out.println("\nTodos os alertas:");
        var all = agroclimaticService.getAllAlertsByProperty(idProperty);
        if (all.isEmpty()) {
            System.out.println("  (nenhum)");
        } else {
            all.forEach(a ->
                    System.out.printf("  [%s | %s | %s] %s%n",
                            a.getAlertType(), a.getSeverity(), a.getDescription(), a.getGeneratedDate())
            );
        }
    }

    public void showHistory() {
        System.out.println("\n--- Histórico de Previsões ---");
        UUID idProperty = resolveActiveProperty();
        if (idProperty == null) return;

        var history = agroclimaticService.consultHistory(idProperty);
        if (history.isEmpty()) {
            System.out.println("Nenhuma previsão registrada.");
        } else {
            history.forEach(f ->
                    System.out.printf(
                            "  [%s] Chuva: %.1fmm | Min: %d°C | Max: %d°C | Vento: %.1f km/h%n",
                            f.getForecastDate(),
                            f.getPrecipitation(),
                            f.getMinTemperature(),
                            f.getMaxTemperature(),
                            f.getWindSpeedKMH()
                    )
            );
        }
    }

    private String getClimateArt(Agroclimatic forecast) {

        if (forecast.getPrecipitation() >= 1) {
            return CHUVA;
        }

        if (forecast.getAirHumidityPercentage() >= 80) {
            return NUBLADO;
        }

        return SOL_E_NUVEM;
    }

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

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número.");
            }
        }
    }

    private String resolveClimaKey(double precip, double minTemp) {
        if (minTemp <= 10)  return "frio";
        if (precip >= 150)  return "chuvoso";
        if (precip < 50)    return "seco";
        return "temperado";
    }
}