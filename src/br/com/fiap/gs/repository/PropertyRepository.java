package br.com.fiap.gs.repository;

import br.com.fiap.gs.model.Farmer;
import br.com.fiap.gs.model.Property;

import java.util.List;
import java.util.Optional;
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
