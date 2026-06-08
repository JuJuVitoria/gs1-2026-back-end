package br.com.fiap.gs.repository.impl.climate;

import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.repository.InMemoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AgroclimaticRepository extends InMemoryRepository<Agroclimatic> {
    @Override
    protected UUID getIdOf(Agroclimatic entity) {
        return entity.getId();
    }

    @Override
    public Agroclimatic save(Agroclimatic entity) {
        super.save(entity);

        removeOldData();

        return entity;
    }

    public List<Agroclimatic> findAllByPropertyID(UUID propertyId) {
        return store.values().stream()
                .filter(p -> p.getIdProperty().equals(propertyId))
                .toList();
    }

    public Optional<Agroclimatic> findTodayForecastByProperty(UUID propertyId) {
        LocalDate today = LocalDate.now();

        return store.values().stream()
                .filter(p -> p.getIdProperty().equals(propertyId))
                .filter(p -> p.getForecastDate().equals(today))
                .findFirst();
    }

    private void removeOldData() {
        LocalDate limitPast = LocalDate.now().minusDays(7);

        store.values().removeIf(p -> p.getForecastDate().isBefore(limitPast));
    }
}