package br.com.fiap.gs.service.interfaces;


import br.com.fiap.gs.model.user.Property;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyService {
    Property registerProperty(UUID idFarmer, String farmName, double latitude, double longitude);
    List<Property> listPropertiesByProducer(UUID idFarmer);
    Optional<Property> searchByID(UUID id);
    void update(Property f);
    void delete(UUID idProperty);
    void setActiveProperty(Property p);
}
