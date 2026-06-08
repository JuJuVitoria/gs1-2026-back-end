package br.com.fiap.gs.repository.config;

import br.com.fiap.gs.enums.*;
import br.com.fiap.gs.model.ai.ChatMessage;
import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.model.user.PlantationRecord;
import br.com.fiap.gs.model.user.Property;
import br.com.fiap.gs.model.ai.AISuggestion;
import br.com.fiap.gs.model.ai.ChatSession;
import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.model.climate.ClimateAlert;
import br.com.fiap.gs.repository.impl.ai.ChatMessageRepository;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.repository.impl.user.ManagementNotebookRepository;
import br.com.fiap.gs.repository.impl.user.PropertyRepository;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.repository.impl.ai.ChatSessionRepository;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;
import br.com.fiap.gs.repository.impl.climate.ClimateAlertRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DataSeeder {
    private final FarmerRepository farmerRepository;
    private final PropertyRepository propertyRepository;
    private final AgroclimaticRepository agroclimaticRepository;
    private final AISuggestionRepository aiSuggestionRepository;
    private final ManagementNotebookRepository managementNotebookRepository;
    private final ClimateAlertRepository climateAlertRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;

    //Mock Data - User 1
    public static final UUID FARMER_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public static final UUID F1_CHATSESSION1 = UUID.fromString("00000000-0000-0000-0000-000001000001");
    public static final UUID F1_CS1_MESSAGE1 = UUID.fromString("00000000-0000-0000-0000-000011000001");
    public static final UUID F1_CHATSESSION2 = UUID.fromString("00000000-0000-0000-0000-000002000001");
    public static final UUID F1_CS2_MESSAGE1 = UUID.fromString("00000000-0000-0000-0000-000012000001");

    public static final UUID FARMER_1_PROPERTY_1  = UUID.fromString("00000000-0000-0000-0000-000000000011");
    public static final UUID F1_P1_AGROCLIMATIC1 = UUID.fromString("00000000-0000-0000-0000-000000000111");
    public static final UUID F1_P1_AISUGGESTION1 = UUID.fromString("00000000-0000-0000-0000-000000001011");
    public static final UUID F1_P1_PLANTATIONRECORD1 = UUID.fromString("00000000-0000-0000-0000-000000010011");
    public static final UUID F1_P1_ALERT1 = UUID.fromString("00000000-0000-0000-0000-000000100011");

    public static final UUID FARMER_1_PROPERTY_2  = UUID.fromString("00000000-0000-0000-0000-000000000021");
    public static final UUID F1_P2_AGROCLIMATIC1 = UUID.fromString("00000000-0000-0000-0000-000000000121");
    public static final UUID F1_P2_AISUGGESTION1 = UUID.fromString("00000000-0000-0000-0000-000000001021");
    public static final UUID F1_P2_PLANTATIONRECORD1 = UUID.fromString("00000000-0000-0000-0000-000000010021");
    public static final UUID F1_P2_ALERT1 = UUID.fromString("00000000-0000-0000-0000-000000100021");


    //Mock Data - User 2
    public static final UUID FARMER_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");

    public static final UUID F2_CHATSESSION1 = UUID.fromString("00000000-0000-0000-0000-000001000002");
    public static final UUID F2_CS1_MESSAGE1 = UUID.fromString("00000000-0000-0000-0000-000011000002");

    public static final UUID FARMER_2_PROPERTY_1  = UUID.fromString("00000000-0000-0000-0000-000000000012");
    public static final UUID F2_P1_AGROCLIMATIC1 = UUID.fromString("00000000-0000-0000-0000-000000000112");
    public static final UUID F2_P1_AISUGGESTION1 = UUID.fromString("00000000-0000-0000-0000-000000001012");
    public static final UUID F2_P1_PLANTATIONRECORD1 = UUID.fromString("00000000-0000-0000-0000-000000010012");
    public static final UUID F2_P1_ALERT1 = UUID.fromString("00000000-0000-0000-0000-000000100012");

    public DataSeeder(FarmerRepository farmerRepository,
                      PropertyRepository propertyRepository,
                      AgroclimaticRepository agroclimaticRepository,
                      AISuggestionRepository aiSuggestionRepository,
                      ManagementNotebookRepository managementNotebookRepository,
                      ClimateAlertRepository climateAlertRepository,
                      ChatSessionRepository chatSessionRepository,
                      ChatMessageRepository chatMessageRepository) {
        this.farmerRepository = farmerRepository;
        this.propertyRepository = propertyRepository;
        this.agroclimaticRepository = agroclimaticRepository;
        this.aiSuggestionRepository = aiSuggestionRepository;
        this.managementNotebookRepository = managementNotebookRepository;
        this.climateAlertRepository = climateAlertRepository;
        this.chatSessionRepository = chatSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
        seed();
    }

    private void seed() {
        //USER 1
        Farmer farmer1 = new Farmer(FARMER_1, "Madalena Silva", "madalena@sistema.com", "senha123");
        farmerRepository.save(farmer1);

        //USER 2 - AI CHAT
        ChatSession f1ChatSession1 = new ChatSession(F1_CHATSESSION1, FARMER_1, "Geadas no inverno", LocalDate.now());
        chatSessionRepository.save(f1ChatSession1);
        ChatMessage f1CS1Message1 = new ChatMessage(F1_CS1_MESSAGE1, F1_CHATSESSION1, SenderType.FARMER,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                LocalDateTime.now());
        chatMessageRepository.save(f1CS1Message1);

        ChatSession f1ChatSession2 = new ChatSession(F1_CHATSESSION2, FARMER_1, "Geadas no inverno", LocalDate.now());
        chatSessionRepository.save(f1ChatSession2);
        ChatMessage f1CS2Message1 = new ChatMessage(F1_CS2_MESSAGE1, F1_CHATSESSION2, SenderType.FARMER,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                LocalDateTime.now());
        chatMessageRepository.save(f1CS2Message1);

        //USER 1 - PROPERTY 1
        Property farmer1Property1 = new Property(FARMER_1_PROPERTY_1, FARMER_1, "Recanto da luz",
                -26.382758443193783, -50.342745780944824);
        propertyRepository.save(farmer1Property1);
        Agroclimatic f1property1Agroclimatic1 = new Agroclimatic(F1_P1_AGROCLIMATIC1, FARMER_1_PROPERTY_1,
                LocalDate.now(), 21, 8, 10, 67, 6);
        agroclimaticRepository.save(f1property1Agroclimatic1);
        AISuggestion f1property1AISuggestion1 = new AISuggestion(F1_P1_AISUGGESTION1, FARMER_1_PROPERTY_1,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                SuggestionType.HARVEST, LocalDate.now() );
        aiSuggestionRepository.save(f1property1AISuggestion1);
        PlantationRecord f1property1PlantionRecord1 = new PlantationRecord(F1_P1_PLANTATIONRECORD1, FARMER_1_PROPERTY_1, ActivityType.PLANTIO,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                LocalDateTime.now());
        managementNotebookRepository.save(f1property1PlantionRecord1);
        ClimateAlert f1property1ClimateAlert1 = new ClimateAlert(F1_P1_ALERT1, FARMER_1_PROPERTY_1,
                "Risco de geada severa", RiskLevel.ALTO,  AlertType.GRANIZO, LocalDate.now(),"ACTIVE");
        climateAlertRepository.save(f1property1ClimateAlert1);

        //USER 1 - PROPERTY 2
        Property farmer1Property2 = new Property(FARMER_1_PROPERTY_2, FARMER_1, "Chácara das flores",
                -26.01328659237649, -51.4017677307129);
        propertyRepository.save(farmer1Property2);
        Agroclimatic f1property2Agroclimatic1 = new Agroclimatic(F1_P2_AGROCLIMATIC1, FARMER_1_PROPERTY_2,
                LocalDate.now(), 21, 8, 10, 67, 6);
        agroclimaticRepository.save(f1property2Agroclimatic1);
        AISuggestion f1property2AISuggestion1 = new AISuggestion(F1_P2_AISUGGESTION1, FARMER_1_PROPERTY_2,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                SuggestionType.HARVEST, LocalDate.now());
        aiSuggestionRepository.save(f1property2AISuggestion1);
        PlantationRecord f1property2PlantionRecord1 = new PlantationRecord(F1_P2_PLANTATIONRECORD1, FARMER_1_PROPERTY_2, ActivityType.PLANTIO,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                LocalDateTime.now());
        managementNotebookRepository.save(f1property2PlantionRecord1);
        ClimateAlert f1property2ClimateAlert1 = new ClimateAlert(F1_P2_ALERT1, FARMER_1_PROPERTY_2,
                "Risco de geada severa", RiskLevel.ALTO,  AlertType.GRANIZO, LocalDate.now(),"ACTIVE");
        climateAlertRepository.save(f1property2ClimateAlert1);

        //USER 2
        Farmer farmer2 = new Farmer(FARMER_2, "João Costa", "joao@sistema.com", "senha456");
        farmerRepository.save(farmer2);

        //USER 2 - AI CHAT
        ChatSession f2ChatSession1= new ChatSession(F2_CHATSESSION1, FARMER_2, "Geadas no inverno", LocalDate.now());
        chatSessionRepository.save(f2ChatSession1);
        ChatMessage f2CS1Message1 = new ChatMessage(F2_CS1_MESSAGE1, F2_CHATSESSION1, SenderType.FARMER,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                LocalDateTime.now());
        chatMessageRepository.save(f2CS1Message1);

        //USER 2 - PROPERTY 1
        Property farmer2Property1 = new Property(FARMER_2_PROPERTY_1, FARMER_2, "Pedacinho do Céu", -20.59807974803205, -49.70712661743165);
        propertyRepository.save(farmer2Property1);
        Agroclimatic f2property1Agroclimatic1 = new Agroclimatic(F2_P1_AGROCLIMATIC1, FARMER_2_PROPERTY_1, LocalDate.now(), 21, 8, 10, 67, 6);
        agroclimaticRepository.save(f2property1Agroclimatic1);
        AISuggestion f2property1AISuggestion1 = new AISuggestion(F2_P1_AISUGGESTION1, FARMER_2_PROPERTY_1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                SuggestionType.HARVEST, LocalDate.now());
        aiSuggestionRepository.save(f2property1AISuggestion1);
        PlantationRecord f2property1PlantionRecord1 = new PlantationRecord(F2_P1_PLANTATIONRECORD1, FARMER_2_PROPERTY_1, ActivityType.PLANTIO,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                LocalDateTime.now());
        managementNotebookRepository.save(f2property1PlantionRecord1);
        ClimateAlert f2property1ClimateAlert1 = new ClimateAlert(F2_P1_ALERT1, FARMER_2_PROPERTY_1, "Risco de geada severa", RiskLevel.ALTO,  AlertType.GRANIZO, LocalDate.now(),"ACTIVE");
        climateAlertRepository.save(f2property1ClimateAlert1);
    }
}
