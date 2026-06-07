package br.com.fiap.gs.repository.impl;

import br.com.fiap.gs.model.PlantationRecord;

import java.util.List;
import java.util.Optional;
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
}
