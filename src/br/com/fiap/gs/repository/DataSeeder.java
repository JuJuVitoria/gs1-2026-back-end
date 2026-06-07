package br.com.fiap.gs.repository;

import br.com.fiap.gs.model.Farmer;
import br.com.fiap.gs.model.Property;

import java.util.UUID;

public class DataSeeder {
    private final FarmerRepository farmerRepository;
    private final PropertyRepository propertyRepository;

    //Mock Data - User 1
    public static final UUID FARMER_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final UUID FARMER_1_PROPERTY_1  = UUID.fromString("00000000-0000-0000-0000-000000000011");
    public static final UUID FARMER_1_PROPERTY_2  = UUID.fromString("00000000-0000-0000-0000-000000000021");

    //Mock Data - User 2
    public static final UUID FARMER_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    public static final UUID FARMER_2_PROPERTY_1  = UUID.fromString("00000000-0000-0000-0000-000000000012");

    public DataSeeder(FarmerRepository farmerRepository, PropertyRepository propertyRepository) {
        this.farmerRepository = farmerRepository;
        this.propertyRepository = propertyRepository;
        seed();
    }

    private void seed() {
        Farmer farmer1 = new Farmer(FARMER_1, "Madalena Silva", "madalena@sistema.com", "senha123");
        Property farmer1Property1 = new Property(FARMER_1_PROPERTY_1, FARMER_1, "Recanto da luz", -26.382758443193783, -50.342745780944824);
        Property farmer1Property2 = new Property(FARMER_1_PROPERTY_2, FARMER_1, "Chácara das flores", -26.01328659237649, -51.4017677307129);

        Farmer farmer2 = new Farmer(FARMER_2, "João Costa", "joao@sistema.com", "senha456");
        Property farmer2Property1 = new Property(FARMER_2_PROPERTY_1, FARMER_2, "Pedacinho do Céu", -20.59807974803205, -49.70712661743165);

        farmerRepository.save(farmer1);
        propertyRepository.save(farmer1Property1);
        propertyRepository.save(farmer1Property2);
        farmerRepository.save(farmer2);
        propertyRepository.save(farmer2Property1);
    }
}
