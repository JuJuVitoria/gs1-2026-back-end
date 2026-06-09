package br.com.fiap.gs.repository.impl.ai;

import br.com.fiap.gs.enums.SuggestionType;
import br.com.fiap.gs.model.ai.AISuggestion;
import br.com.fiap.gs.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AISuggestionRepository extends InMemoryRepository<AISuggestion> {

    @Override
    protected UUID getIdOf(AISuggestion entity) {
        return entity.getId();
    }

    public List<AISuggestion> findByPropertyId(UUID propertyId) {
        return findAll()
                .stream()
                .filter(s -> s.getIdProperty().equals(propertyId))
                .toList();
    }

    public List<AISuggestion> findByPropertyIdAndType(UUID propertyId, SuggestionType type) {
        return findByPropertyId(propertyId)
                .stream()
                .filter(s -> s.getSuggestionType() == type)
                .toList();
    }

    public Optional<AISuggestion> findLatestByPropertyIdAndType(UUID propertyId, SuggestionType type) {
        return findByPropertyIdAndType(propertyId, type)
                .stream()
                .max((a, b) -> a.getDataGenerated().compareTo(b.getDataGenerated()));
    }

    public AISuggestion saveOrReplace(AISuggestion suggestion) {
        findByPropertyId(suggestion.getIdProperty())
                .stream()
                .filter(s -> s.getSuggestionType() == suggestion.getSuggestionType())
                .map(AISuggestion::getId)
                .forEach(this::deleteById);

        return save(suggestion);
    }
}