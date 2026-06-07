package br.com.fiap.gs.repository.impl.climate;

import br.com.fiap.gs.enums.AlertType;
import br.com.fiap.gs.model.climate.ClimateAlert;
import br.com.fiap.gs.repository.impl.InMemoryRepository;

import java.util.List;
import java.util.UUID;

public class ClimateAlertRepository extends InMemoryRepository<ClimateAlert> {

    @Override
    protected UUID getIdOf(ClimateAlert alert) {
        return alert.getId();
    }

    public List<ClimateAlert> findAllByPropertyId(UUID propertyId) {
        return store.values().stream()
                .filter(a -> a.getIdProperty().equals(propertyId))
                .toList();
    }

    public List<ClimateAlert> findByAlertType(AlertType alertType) {
        return store.values().stream()
                .filter(a -> a.getAlertType() == alertType)
                .toList();
    }

    public List<ClimateAlert> findActiveByPropertyId(UUID propertyId) {
        return store.values().stream()
                .filter(a -> a.getIdProperty().equals(propertyId)
                        && a.getStatus().equalsIgnoreCase("ACTIVE"))
                .toList();
    }
}