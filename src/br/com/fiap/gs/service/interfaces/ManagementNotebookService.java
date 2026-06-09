package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.enums.ActivityType;
import br.com.fiap.gs.model.user.PlantationRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManagementNotebookService {
    void registerManagementActivity(UUID idProperty, ActivityType activityType, String content, LocalDateTime registrationDate);
    List<PlantationRecord> listNotebookByProperty(UUID idProp);
    void deleteActivity(UUID id);
    Optional<PlantationRecord> findById(UUID id);
    List<PlantationRecord> filterByType(ActivityType type, UUID idProp);
    PlantationRecord getLatestActivity(UUID idProp);
    String getCurrentPhase(UUID idProp);
}
