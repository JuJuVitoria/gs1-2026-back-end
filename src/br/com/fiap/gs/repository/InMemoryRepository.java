package br.com.fiap.gs.repository;

import java.util.*;

public abstract class InMemoryRepository<T> {

    protected final Map<UUID, T> store = new HashMap<>();

    protected abstract UUID getIdOf(T entity);

    public T save(T entity) {
        store.put(getIdOf(entity), entity);
        return entity;
    }

    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(UUID id) {
        store.remove(id);
    }

    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    public int count() {
        return store.size();
    }
}