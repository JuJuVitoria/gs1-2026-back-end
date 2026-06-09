package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.enums.AlertType;
import br.com.fiap.gs.enums.LifeCycle;
import br.com.fiap.gs.enums.RiskLevel;
import br.com.fiap.gs.model.climate.Agroclimatic;
import br.com.fiap.gs.model.climate.ClimateAlert;
import br.com.fiap.gs.repository.impl.climate.AgroclimaticRepository;
import br.com.fiap.gs.repository.impl.climate.ClimateAlertRepository;
import br.com.fiap.gs.service.interfaces.ClimateService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClimateServiceImpl implements ClimateService {
    private final AgroclimaticRepository agroclimaticRepository;
    private final ClimateAlertRepository climateAlertRepository;

    public ClimateServiceImpl(AgroclimaticRepository agroclimaticRepository,
                              ClimateAlertRepository climateAlertRepository) {
        this.agroclimaticRepository = agroclimaticRepository;
        this.climateAlertRepository = climateAlertRepository;
    }

    @Override
    public void registerAgroclimatic(Agroclimatic forecast) {
        agroclimaticRepository.save(forecast);
    }

    @Override
    public Optional<Agroclimatic> getTodayForecast(UUID idProperty) {
        return agroclimaticRepository.findTodayForecastByProperty(idProperty);
    }

    @Override
    public List<Agroclimatic> consultHistory(UUID idProperty) {
        return agroclimaticRepository.findAllByPropertyID(idProperty);
    }

    @Override
    public boolean checkRain(Agroclimatic climate) {
        return climate.getPrecipitation() >= 50;
    }

    @Override
    public boolean checkFrost(Agroclimatic climate) {
        return climate.getMinTemperature() <= 3.0;
    }

    @Override
    public boolean checkWind(Agroclimatic climate) {
        return climate.getWindSpeedKMH() >= 50;
    }

    @Override
    public ClimateAlert generateClimateAlert(Agroclimatic climate) {
        if (checkFrost(climate)) {
            return new ClimateAlert(
                    climate.getIdProperty(),
                    "Risco de Geada - Temperatura mínima prevista abaixo de 3°C.",
                    RiskLevel.CRITICO, AlertType.GEADA,
                    LocalDate.now(), LifeCycle.ATIVO
            );
        }
        if (checkWind(climate)) {
            return new ClimateAlert(
                    climate.getIdProperty(),
                    "Vento Forte - Rajadas acima de 50 km/h.",
                    RiskLevel.ALTO, AlertType.VENTO_FORTE,
                    LocalDate.now(), LifeCycle.ATIVO
            );
        }
        if (checkRain(climate)) {
            return new ClimateAlert(
                    climate.getIdProperty(),
                    "Chuva Intensa - Volume de chuva elevado previsto.",
                    RiskLevel.ALTO, AlertType.CHUVA_INTENSA,
                    LocalDate.now(), LifeCycle.ATIVO
            );
        }
        if (climate.getPrecipitation() < 5) {
            return new ClimateAlert(
                    climate.getIdProperty(),
                    "Estiagem - Possível período de estiagem.",
                    RiskLevel.MEDIO, AlertType.ESTIAGEM,
                    LocalDate.now(), LifeCycle.ATIVO
            );
        }
        if (climate.getAirHumidityPercentage() < 30) {
            return new ClimateAlert(
                    climate.getIdProperty(),
                    "Umidade Baixa - Atenção ao estresse hídrico das culturas.",
                    RiskLevel.MEDIO, AlertType.SECA,
                    LocalDate.now(), LifeCycle.ATIVO
            );
        }
        return null;
    }

    @Override
    public List<ClimateAlert> getAllAlertsByProperty(UUID idProperty) {
        return climateAlertRepository.findAllByPropertyId(idProperty);
    }

    @Override
    public Optional<ClimateAlert> getActiveAlerts(UUID idProperty) {
        return climateAlertRepository.findActiveByPropertyId(idProperty);
    }
}
