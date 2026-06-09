package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.service.interfaces.FarmerService;

import java.util.List;
import java.util.UUID;

public class FarmerServiceImpl implements FarmerService {
    private Farmer loggedUser;
    private final FarmerRepository farmerRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    public Farmer registerFarmer(String name, String email, String password) {
        if (name == null || name.isBlank())     throw new IllegalArgumentException("Nome obrigatório.");
        if (email == null || email.isBlank())   throw new IllegalArgumentException("E-mail obrigatório.");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Senha obrigatória.");

        if (farmerRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("E-mail já cadastrado: " + email);
        }

        Farmer f = new Farmer(UUID.randomUUID(), name, email, password);
        return farmerRepository.save(f);
    }

    @Override
    public boolean authFarmer(String email, String password) {
        return farmerRepository.findByEmail(email)
                .map(f -> f.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public Farmer searchByID(UUID id) {
        return farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + id));
    }

    @Override
    public Farmer searchByEmail(String email) {
        return farmerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + email));
    }

    @Override
    public Farmer getLoggedFarmer() {
        return loggedUser;
    }

    @Override
    public void delete(Farmer f) {
        farmerRepository.deleteById(f.getId());
    }

    @Override
    public void update(Farmer f) {
        farmerRepository.save(f);
    }

    public void setLoggedUser(Farmer loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Farmer> getListFarmer() {
        return farmerRepository.findAll();
    }

}
