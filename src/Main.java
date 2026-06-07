import br.com.fiap.gs.repository.config.DataSeeder;
import br.com.fiap.gs.repository.impl.FarmerRepository;
import br.com.fiap.gs.repository.impl.PropertyRepository;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;

public class Main {
    public static void main(String[] args) {
        FarmerRepository farmerRepository = new FarmerRepository();
        PropertyRepository propertyRepository = new PropertyRepository();
        AgroclimaticRepository agroclimaticRepository = new AgroclimaticRepository();
        AISuggestionRepository aiSuggestionRepository = new AISuggestionRepository();
        new DataSeeder(farmerRepository, propertyRepository, agroclimaticRepository, aiSuggestionRepository);

        // Buscar todas as propriedades do Farmer 1
        System.out.println("=== Propriedades da Madalena ===");
        propertyRepository.findAllByFarmerID(DataSeeder.FARMER_1)
                .forEach(p -> System.out.println("- " + p.getFarmName()));
        System.out.println("\nPrevisão do tempo: " + agroclimaticRepository.findTodayForecastByProperty(DataSeeder.FARMER_1_PROPERTY_1));
        System.out.println("Sugestão IA: " + aiSuggestionRepository.findByPropertyId(DataSeeder.FARMER_1_PROPERTY_1));

        // Buscar todas as propriedades do Farmer 2
        System.out.println("\n \n=== Propriedades do João ===");
        propertyRepository.findAllByFarmerID(DataSeeder.FARMER_2)
                .forEach(p -> System.out.println("- " + p.getFarmName()));
        System.out.println("\nPrevisão do tempo: " + agroclimaticRepository.findTodayForecastByProperty(DataSeeder.FARMER_2_PROPERTY_1));
        System.out.println("Sugestão IA: " + aiSuggestionRepository.findByPropertyId(DataSeeder.FARMER_2_PROPERTY_1));
    }
}