package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.model.user.Farmer;

import java.util.Optional;

public interface AuthService {
    Farmer register(String name, String email, String password);
    Optional<Farmer> login(String email, String password);
    void logout();
}
