import br.com.fiap.gs.model.*;
import br.com.fiap.gs.repository.DataSeeder;
import br.com.fiap.gs.repository.FarmerRepository;

public class Main {
    public static void main(String[] args) {
        FarmerRepository farmerRepository = new FarmerRepository(); // ← nasce aqui!
        new DataSeeder(farmerRepository);

        System.out.println("Teste findAll: ");
        farmerRepository.findAll()
                .forEach(u -> System.out.println("- " + u.getName()));
        System.out.println("\nTeste findByEmail (Buscando madalena@sistema.com...): ");
        System.out.println(farmerRepository.findByEmail("madalena@sistema.com"));

        System.out.println("\nTeste de adicionar new user: ");
        Farmer newFarmer = new Farmer("Paula", "paula@gmail.com", "1234");
        System.out.println("Adicionando...  " + newFarmer);
        farmerRepository.save(newFarmer);
        System.out.println("\nTeste findAll após inserção de novo usuário: ");
        farmerRepository.findAll()
                .forEach(u -> System.out.println("- " + u.getName()));

        System.out.println("\nTeste delete do novo usuário: ");
        farmerRepository.deleteById(newFarmer.getId());
        System.out.println("\nTeste findAll após o delete do novo usuário: ");
        farmerRepository.findAll()
                .forEach(u -> System.out.println("- " + u.getName()));

    }
}