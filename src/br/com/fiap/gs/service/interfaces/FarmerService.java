package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.model.user.Farmer;

import java.util.UUID;

public interface FarmerService {
    Farmer registerFarmer(String name, String email, String password);
    boolean authFarmer(String email, String password);
    Farmer searchByID(UUID id);
    Farmer searchByEmail(String email);
    void update(Farmer f);
    void delete(Farmer f);
    Farmer getLoggedFarmer();
}
