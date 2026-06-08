package br.com.fiap.gs.service.interfaces;

import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.model.climate.ClimateAlert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClimateService {
    // --- Agroclimatic ---
    void registerAgroclimatic(Agroclimatic forecast);
    Optional<Agroclimatic> getTodayForecast(UUID idProperty);
    List<Agroclimatic> consultHistory(UUID idProperty);

    // --- Checks internos ---
    boolean checkRain(Agroclimatic climate);
    boolean checkFrost(Agroclimatic climate);
    boolean checkWind(Agroclimatic climate);

    // --- ClimateAlert ---
    ClimateAlert generateClimateAlert(Agroclimatic climate);
    List<ClimateAlert>  getAllAlertsByProperty(UUID idProperty);
    Optional<ClimateAlert> getActiveAlerts(UUID idProperty);
}