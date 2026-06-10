package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.model.user.Farmer;

import java.util.Optional;
import java.util.UUID;

public interface FarmerService {
    Farmer searchById(UUID id);
    Farmer searchByEmail(String email);
    void update(Farmer f);
    void delete(Farmer f);
}
