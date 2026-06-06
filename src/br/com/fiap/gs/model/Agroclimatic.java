package br.com.fiap.gs.model;

import java.time.LocalDateTime;

public class Agroclimatic {
    private long id;
    private long idProperty;
    private LocalDateTime forecastDate;
    private int maxTempeture;
    private int minTempeture;
    private double precipitation;
    private int airHumidityPercentage;
    private double windSpeedKMH;

    public Agroclimatic(long idProperty, LocalDateTime forecastDate, int maxTempeture, int minTempeture, double precipitation, int airHumidityPercentage, double windSpeedKMH) {
        this.idProperty = idProperty;
        this.forecastDate = forecastDate;
        this.maxTempeture = maxTempeture;
        this.minTempeture = minTempeture;
        this.precipitation = precipitation;
        this.airHumidityPercentage = airHumidityPercentage;
        this.windSpeedKMH = windSpeedKMH;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(long idProperty) {
        this.idProperty = idProperty;
    }

    public LocalDateTime getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(LocalDateTime forecastDate) {
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
        return "Previsão do dia " + this.forecastDate + " - Temperatura máxima do dia: " + this.maxTempeture + " | Temperatura minima do dia: " + this.minTempeture + " - Precipitação: " + this.precipitation + " | Humidade: " + this.airHumidityPercentage + " | Vento km/h: " + this.windSpeedKMH + " - ID da propriedade: " + this.idProperty;
    }
}
