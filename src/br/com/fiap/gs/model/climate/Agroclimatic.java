package br.com.fiap.gs.model.climate;

import java.time.LocalDate;
import java.util.UUID;

public class Agroclimatic {
    private UUID id;
    private UUID idProperty;
    private LocalDate forecastDate;
    private int maxTempeture;
    private int minTempeture;
    private double precipitation;
    private int airHumidityPercentage;
    private double windSpeedKMH;

    public Agroclimatic(UUID idProperty, LocalDate forecastDate, int maxTempeture,
                        int minTempeture, double precipitation, int airHumidityPercentage,
                        double windSpeedKMH) {
        this.id = UUID.randomUUID();
        this.idProperty = idProperty;
        this.forecastDate = forecastDate;
        this.maxTempeture = maxTempeture;
        this.minTempeture = minTempeture;
        this.precipitation = precipitation;
        this.airHumidityPercentage = airHumidityPercentage;
        this.windSpeedKMH = windSpeedKMH;
    }

    public Agroclimatic(UUID id, UUID idProperty, LocalDate forecastDate, int maxTempeture,
                        int minTempeture, double precipitation, int airHumidityPercentage,
                        double windSpeedKMH) {
        this.id = id;
        this.idProperty = idProperty;
        this.forecastDate = forecastDate;
        this.maxTempeture = maxTempeture;
        this.minTempeture = minTempeture;
        this.precipitation = precipitation;
        this.airHumidityPercentage = airHumidityPercentage;
        this.windSpeedKMH = windSpeedKMH;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(UUID idProperty) {
        this.idProperty = idProperty;
    }

    public LocalDate getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(LocalDate forecastDate) {
        this.forecastDate = forecastDate;
    }

    public int getMaxTempeture() {
        return maxTempeture;
    }

    public void setMaxTempeture(int maxTempeture) {
        this.maxTempeture = maxTempeture;
    }

    public int getMinTempeture() {
        return minTempeture;
    }

    public void setMinTempeture(int minTempeture) {
        this.minTempeture = minTempeture;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public int getAirHumidityPercentage() {
        return airHumidityPercentage;
    }

    public void setAirHumidityPercentage(int airHumidityPercentage) {
        this.airHumidityPercentage = airHumidityPercentage;
    }

    public double getWindSpeedKMH() {
        return windSpeedKMH;
    }

    public void setWindSpeedKMH(double windSpeedKMH) {
        this.windSpeedKMH = windSpeedKMH;
    }

    @Override
    public String toString() {
        return "Previsão do dia " + this.forecastDate + " - Temperatura máxima do dia: " +
                this.maxTempeture + " | Temperatura minima do dia: " + this.minTempeture +
                " - Precipitação: " + this.precipitation + " | Humidade: " + this.airHumidityPercentage +
                " | Vento km/h: " + this.windSpeedKMH + " - ID da propriedade: " + this.idProperty;
    }
}
