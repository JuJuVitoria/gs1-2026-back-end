import br.com.fiap.gs.repository.config.DataSeeder;
import br.com.fiap.gs.repository.impl.ai.ChatMessageRepository;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.repository.impl.user.ManagementNotebookRepository;
import br.com.fiap.gs.repository.impl.user.PropertyRepository;
import br.com.fiap.gs.repository.impl.ai.AISuggestionRepository;
import br.com.fiap.gs.repository.impl.ai.ChatSessionRepository;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;
import br.com.fiap.gs.repository.impl.climate.ClimateAlertRepository;
import br.com.fiap.gs.service.impl.FarmerServiceImpl;

public class Main {
    public static void main(String[] args) {
        FarmerRepository farmerRepository = new FarmerRepository();
        PropertyRepository propertyRepository = new PropertyRepository();
        AgroclimaticRepository agroclimaticRepository = new AgroclimaticRepository();
        AISuggestionRepository aiSuggestionRepository = new AISuggestionRepository();
        ManagementNotebookRepository managementNotebookRepository = new ManagementNotebookRepository();
        ClimateAlertRepository climateAlertRepository = new ClimateAlertRepository();
        ChatSessionRepository chatSessionRepository = new ChatSessionRepository();
        ChatMessageRepository chatMessageRepository = new ChatMessageRepository();

        FarmerServiceImpl farmerService = new FarmerServiceImpl(farmerRepository);

        new DataSeeder(farmerRepository,
                propertyRepository,
                agroclimaticRepository,
                aiSuggestionRepository,
                managementNotebookRepository,
                climateAlertRepository,
                chatSessionRepository,
                chatMessageRepository);

        farmerService.getListFarmer().forEach(f ->
                System.out.println("Farmer carregado: " + f.getName() + " | " + f.getEmail())
        );

        boolean auth = farmerService.authFarmer("madalena@astrocrop.com.br", "senha123");
        System.out.println("Login Madalena: " + auth);

        if (auth) {
            farmerService.setLoggedUser(
                    farmerService.searchByEmail("madalena@astrocrop.com.br")
            );
            System.out.println("Usuário logado: " + farmerService.getLoggedFarmer().getName());
        }
    }
}