package br.com.fiap.gs.repository.config;

import br.com.fiap.gs.enums.SuggestionType;
import br.com.fiap.gs.model.Farmer;
import br.com.fiap.gs.model.Property;
import br.com.fiap.gs.model.ai.AISuggestion;
import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.repository.impl.FarmerRepository;
import br.com.fiap.gs.repository.impl.PropertyRepository;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;

import java.time.LocalDate;
import java.util.UUID;

public class DataSeeder {
    private final FarmerRepository farmerRepository;
    private final PropertyRepository propertyRepository;
    private final AgroclimaticRepository agroclimaticRepository;
    private final AISuggestionRepository aiSuggestionRepository;

    //Mock Data - User 1
    public static final UUID FARMER_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final UUID FARMER_1_PROPERTY_1  = UUID.fromString("00000000-0000-0000-0000-000000000011");
    public static final UUID F1_PROPERTY_1_AGROCLIMATIC_1 = UUID.fromString("00000000-0000-0000-0000-000000000111");
    public static final UUID F1_PROPERTY_1_AISUGGESTION_1 = UUID.fromString("00000000-0000-0000-0000-000000001011");

    public static final UUID FARMER_1_PROPERTY_2  = UUID.fromString("00000000-0000-0000-0000-000000000021");
    public static final UUID F1_PROPERTY_2_AGROCLIMATIC_1 = UUID.fromString("00000000-0000-0000-0000-000000000121");
    public static final UUID F1_PROPERTY_2_AISUGGESTION_1 = UUID.fromString("00000000-0000-0000-0000-000000001021");

    //Mock Data - User 2
    public static final UUID FARMER_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    public static final UUID FARMER_2_PROPERTY_1  = UUID.fromString("00000000-0000-0000-0000-000000000012");
    public static final UUID F2_PROPERTY_1_AGROCLIMATIC_1 = UUID.fromString("00000000-0000-0000-0000-000000000112");
    public static final UUID F2_PROPERTY_1_AISUGGESTION_1 = UUID.fromString("00000000-0000-0000-0000-000000001012");

    public DataSeeder(FarmerRepository farmerRepository,
                      PropertyRepository propertyRepository,
                      AgroclimaticRepository agroclimaticRepository,
                      AISuggestionRepository aiSuggestionRepository) {
        this.farmerRepository = farmerRepository;
        this.propertyRepository = propertyRepository;
        this.agroclimaticRepository = agroclimaticRepository;
        this.aiSuggestionRepository = aiSuggestionRepository;
        seed();
    }

    private void seed() {
        Farmer farmer1 = new Farmer(FARMER_1, "Madalena Silva", "madalena@sistema.com", "senha123");
        Property farmer1Property1 = new Property(FARMER_1_PROPERTY_1, FARMER_1, "Recanto da luz", -26.382758443193783, -50.342745780944824);
        Agroclimatic f1property1Agroclimatic1 = new Agroclimatic(F1_PROPERTY_1_AGROCLIMATIC_1, FARMER_1_PROPERTY_1, LocalDate.now(), 21, 8, 10, 67, 6);
        AISuggestion f1property1AISuggestion1 = new AISuggestion(F1_PROPERTY_1_AISUGGESTION_1, FARMER_1_PROPERTY_1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                SuggestionType.HARVEST, LocalDate.now() );
        Property farmer1Property2 = new Property(FARMER_1_PROPERTY_2, FARMER_1, "Chácara das flores", -26.01328659237649, -51.4017677307129);
        Agroclimatic f1property2Agroclimatic1 = new Agroclimatic(F1_PROPERTY_2_AGROCLIMATIC_1, FARMER_1_PROPERTY_2, LocalDate.now(), 21, 8, 10, 67, 6);
        AISuggestion f1property2AISuggestion1 = new AISuggestion(F1_PROPERTY_2_AISUGGESTION_1, FARMER_1_PROPERTY_2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                SuggestionType.HARVEST, LocalDate.now());

        Farmer farmer2 = new Farmer(FARMER_2, "João Costa", "joao@sistema.com", "senha456");
        Property farmer2Property1 = new Property(FARMER_2_PROPERTY_1, FARMER_2, "Pedacinho do Céu", -20.59807974803205, -49.70712661743165);
        Agroclimatic f2property1Agroclimatic1 = new Agroclimatic(F2_PROPERTY_1_AGROCLIMATIC_1, FARMER_2_PROPERTY_1, LocalDate.now(), 21, 8, 10, 67, 6);
        AISuggestion f2property1AISuggestion1 = new AISuggestion(F2_PROPERTY_1_AISUGGESTION_1, FARMER_2_PROPERTY_1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque turpis leo, aliquet vel ipsum in, dictum tincidunt nulla.",
                SuggestionType.HARVEST, LocalDate.now());

        //USER 1
        farmerRepository.save(farmer1);
        //USER 1 - PROPERTY 1
        propertyRepository.save(farmer1Property1);
        agroclimaticRepository.save(f1property1Agroclimatic1);
        aiSuggestionRepository.save(f1property1AISuggestion1);
        //USER 1 - PROPERTY 2
        propertyRepository.save(farmer1Property2);
        agroclimaticRepository.save(f1property2Agroclimatic1);
        aiSuggestionRepository.save(f1property2AISuggestion1);

        //USER 2
        farmerRepository.save(farmer2);
        //USER 2 - PROPERTY 1
        propertyRepository.save(farmer2Property1);
        agroclimaticRepository.save(f2property1Agroclimatic1);
        aiSuggestionRepository.save(f2property1AISuggestion1);
    }
}
