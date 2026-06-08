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
    public void registerFarmer(Farmer f, String name, String email, String password) {
        f.setName(name);
        f.setEmail(email);
        f.setPassword(password);
        Farmer saved = farmerRepository.save(f);
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

    }

    @Override
    public void update(Farmer f) {

    }

    public void setLoggedUser(Farmer loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Farmer> getListFarmer() {
        return farmerRepository.findAll();
    }

}
