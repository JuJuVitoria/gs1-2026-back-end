package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.repository.impl.user.FarmerRepository;
import br.com.fiap.gs.service.interfaces.AuthService;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private Farmer loggedUser;
    private final FarmerRepository farmerRepository;

    public AuthServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    public Farmer register(String name, String email, String password) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail obrigatório.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Senha obrigatória.");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                    "A senha deve conter pelo menos 6 caracteres."
            );
        }

        if (farmerRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(
                    "Já existe um usuário cadastrado com este e-mail."
            );
        }

        Farmer farmer = new Farmer(name, email, password);

        return farmerRepository.save(farmer);
    }

    @Override
    public Optional<Farmer> login(String email, String password) {

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }

        Optional<Farmer> farmer = farmerRepository.findByEmail(email)
                .filter(f -> f.getPassword().equals(password));

        farmer.ifPresent(user -> loggedUser = user);

        return farmer;
    }

    @Override
    public void logout() {
        loggedUser = null;
    }

    public Farmer getLoggedUser() {
        return loggedUser;
    }

    public boolean isAuthenticated() {
        return loggedUser != null;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}