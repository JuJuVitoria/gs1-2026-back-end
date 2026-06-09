package br.com.fiap.gs.repository.impl.user;

import br.com.fiap.gs.enums.ActivityType;
import br.com.fiap.gs.model.user.PlantationRecord;
import br.com.fiap.gs.repository.InMemoryRepository;

import java.util.List;
import java.util.UUID;

public class ManagementNotebookRepository extends InMemoryRepository<PlantationRecord> {
    @Override
    protected UUID getIdOf(PlantationRecord entity) {
        return entity.getId();
    }

    public List<PlantationRecord> findAllNotesByPropertyID(UUID propertyID) {
        return store.values().stream()
                .filter(p -> p.getIdProperty().equals(propertyID))
                .toList();
    }

    public List<PlantationRecord> findAllNotesByTypeAndProperty(ActivityType type, UUID idProperty) {
        return store.values().stream()
                .filter(p -> p.getActivityType().equals(type) && p.getIdProperty().equals(idProperty))
                .toList();
    }
}
