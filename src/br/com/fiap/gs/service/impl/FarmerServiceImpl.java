package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.service.interfaces.FarmerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FarmerServiceImpl implements FarmerService {

    private Farmer loggedUser;
    private final FarmerRepository farmerRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }


    @Override
    public Farmer searchById(UUID id) {
        return farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer não encontrado: " + id));
    }

    @Override
    public Farmer searchByEmail(String email) {
        return farmerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Farmer não encontrado: " + email));
    }

    @Override
    public void update(Farmer farmer) {
        farmerRepository.save(farmer);
    }

    @Override
    public void delete(Farmer farmer) {
        farmerRepository.deleteById(farmer.getId());
    }

    public List<Farmer> listAll() {
        return farmerRepository.findAll();
    }
}