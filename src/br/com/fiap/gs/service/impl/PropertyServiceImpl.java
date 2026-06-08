package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.model.user.Property;
import br.com.fiap.gs.repository.impl.user.PropertyRepository;
import br.com.fiap.gs.service.interfaces.PropertyService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PropertyServiceImpl implements PropertyService {
    private Property activeProperty;
    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property registerProperty(UUID idFarmer, String farmName, double latitude, double longitude) {
        if (idFarmer == null)
            throw new IllegalArgumentException("ID do Farmer obrigatório.");
        if (farmName == null || farmName.isBlank())
            throw new IllegalArgumentException("Nome da propriedade obrigatório.");
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("Latitude inválida: deve estar entre -90 e 90.");
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("Longitude inválida: deve estar entre -180 e 180.");

        Property p = new Property(idFarmer, farmName, latitude, longitude);
        return propertyRepository.save(p);
    }

    @Override
    public List<Property> listPropertiesByProducer(UUID idFarmer) {
        return propertyRepository.findAllByFarmerID(idFarmer);
    }

    @Override
    public Optional<Property> searchByID(UUID id) {
        return propertyRepository.findById(id);
    }

    @Override
    public void update(Property f) {

    }

    @Override
    public void delete(UUID idProperty) {

    }

    @Override
    public void setActiveProperty(Property p) {
        this.activeProperty = p;
    }

    public Property getActiveProperty() { return this.activeProperty; }

    public List<Property> getListProperty() { return propertyRepository.findAll(); }
}
