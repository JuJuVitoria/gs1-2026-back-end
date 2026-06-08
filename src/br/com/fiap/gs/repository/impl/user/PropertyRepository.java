package br.com.fiap.gs.repository.impl.user;

import br.com.fiap.gs.model.user.Property;
import br.com.fiap.gs.repository.InMemoryRepository;

import java.util.List;
import java.util.UUID;

public class PropertyRepository extends InMemoryRepository<Property> {
    @Override
    protected UUID getIdOf(Property property) {
        return property.getId();
    }

    public List<Property> findAllByFarmerID(UUID farmerId) {
        return store.values().stream()
                .filter(p -> p.getIdFarmer().equals(farmerId))
                .toList();
    }
}
