package br.com.fiap.gs.repository.impl.user;

import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FarmerRepository extends InMemoryRepository<Farmer> {
    @Override
    protected UUID getIdOf(Farmer farmer) {
        return farmer.getId();
    }

    public Optional<Farmer> findByEmail(String email) {
        return store.values().stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }

    public List<Farmer> findByName(String name) {
        return store.values().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .toList();
    }
}
