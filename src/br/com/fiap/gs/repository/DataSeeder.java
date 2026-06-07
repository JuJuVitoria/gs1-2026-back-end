package br.com.fiap.gs.repository;

import br.com.fiap.gs.model.Farmer;

import java.util.UUID;

public class DataSeeder {
    private final FarmerRepository farmerRepository;

    public static final UUID FARMER_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final UUID FARMER_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");

    public DataSeeder(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
        seed();
    }

    private void seed() {
        Farmer farmer1 = new Farmer(FARMER_1, "Madalena Silva", "madalena@sistema.com", "senha123");
        Farmer farmer2  = new Farmer(FARMER_2, "João Costa", "joao@sistema.com", "senha456");

        farmerRepository.save(farmer1);
        farmerRepository.save(farmer2);
    }
}
