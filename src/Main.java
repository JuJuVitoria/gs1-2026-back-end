import br.com.fiap.gs.enums.ActivityType;
import br.com.fiap.gs.enums.AlertType;
import br.com.fiap.gs.enums.RiskLevel;
import br.com.fiap.gs.enums.SenderType;
import br.com.fiap.gs.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    static Farmer user1 = new Farmer("João", "joao@gmil.com", "1234");
    static Property property1User1 = new Property(1, "Recanto da Luz", ".", ".");
    static Agroclimatic agroclimatic1property1 = new Agroclimatic(0, LocalDateTime.now(), 22, 7, 0, 64, 5);
    static ClimateAlert climateAlert1property1 = new ClimateAlert(0, "Risco de fortes chuvas!", RiskLevel.CRITICO, AlertType.CHUVA_INTENSA, LocalDate.now());
    static PlantationRecord plantationRecord1property1 = new PlantationRecord(0, ActivityType.PLANTIO, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur pulvinar finibus efficitur.", LocalDateTime.now());
    static AISuggestion aiSuggestionproperty1 = new AISuggestion(0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur pulvinar finibus efficitur.", LocalDate.now());
    static ChatSession chatSession1 = new ChatSession(0,"Colheita de milho", LocalDate.now());
    static ChatMessage chatMessage1 = new ChatMessage(0, SenderType.FARMER, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur pulvinar finibus efficitur.", LocalDateTime.now());

    public static void main(String[] args) {
        System.out.println("TESTANDO AS CLASSES: \n");
        System.out.println("Farmer: " + user1);
        System.out.println("Property: " + property1User1);
        System.out.println("Agroclimatic: " + agroclimatic1property1);
        System.out.println("ClimateAlert: " + climateAlert1property1);
        System.out.println("PlantationRecord: " + plantationRecord1property1);
        System.out.println("AISuggestion: " + aiSuggestionproperty1);
        System.out.println("ChatSession: " + chatSession1);
        System.out.println("ChatMessage: " + chatMessage1);
    }
}