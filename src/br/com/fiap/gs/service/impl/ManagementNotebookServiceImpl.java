package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.enums.ActivityType;
import br.com.fiap.gs.model.user.PlantationRecord;
import br.com.fiap.gs.repository.impl.user.ManagementNotebookRepository;
import br.com.fiap.gs.service.interfaces.ManagementNotebookService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ManagementNotebookServiceImpl implements ManagementNotebookService {
    private final ManagementNotebookRepository managementRepo;
    private ActivityType activityTypeFilter;

    public ManagementNotebookServiceImpl(ManagementNotebookRepository managementRepo) {
        this.managementRepo = managementRepo;
    }

    @Override
    public void registerManagementActivity(UUID idProperty, ActivityType activityType, String content, LocalDateTime registrationDate) {
        PlantationRecord record = new PlantationRecord(idProperty, activityType, content, registrationDate);
        managementRepo.save(record);
    }

    @Override
    public List<PlantationRecord> listNotebookByProperty(UUID idProp) {
        return managementRepo.findAllNotesByPropertyID(idProp);
    }

    @Override
    public void deleteActivity(UUID id) {
        managementRepo.deleteById(id);
    }

    @Override
    public Optional<PlantationRecord> findById(UUID id) {
        return managementRepo.findById(id);
    }

    @Override
    public List<PlantationRecord> filterByType(ActivityType type, UUID idProp) {
        return managementRepo.findAllNotesByTypeAndProperty(type, idProp);
    }

    @Override
    public PlantationRecord getLatestActivity(UUID idProp) {
        return managementRepo.findAllNotesByPropertyID(idProp).stream()
                .max(Comparator.comparing(PlantationRecord::getRegistrationDate))
                .orElse(null);
    }

    @Override
    public String getCurrentPhase(UUID idProp) {
        PlantationRecord latest = getLatestActivity(idProp);
        if (latest == null) return "Sem atividades registradas";
        return latest.getActivityType().toString();
    }
}
