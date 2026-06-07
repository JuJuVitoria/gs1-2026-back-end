import br.com.fiap.gs.model.*;
import br.com.fiap.gs.repository.DataSeeder;
import br.com.fiap.gs.repository.FarmerRepository;
import br.com.fiap.gs.repository.PropertyRepository;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        FarmerRepository farmerRepository = new FarmerRepository();
        PropertyRepository propertyRepository = new PropertyRepository();
        new DataSeeder(farmerRepository, propertyRepository);

        System.out.println("Teste findAll: ");
        farmerRepository.findAll()
                .forEach(u -> System.out.println("- " + u.getName()));
        System.out.println("\nTeste findByEmail (Buscando madalena@sistema.com...): ");
        System.out.println(farmerRepository.findByEmail("madalena@sistema.com"));

        // Buscar todas as propriedades do Farmer 1
        System.out.println("=== Propriedades da Madalena ===");
        propertyRepository.findAllByFarmerID(DataSeeder.FARMER_1)
                .forEach(p -> System.out.println("- " + p.getFarmName()));

        // Buscar todas as propriedades do Farmer 2
        System.out.println("=== Propriedades do João ===");
        propertyRepository.findAllByFarmerID(DataSeeder.FARMER_2)
                .forEach(p -> System.out.println("- " + p.getFarmName()));
    }
}