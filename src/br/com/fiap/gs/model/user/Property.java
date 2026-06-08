package br.com.fiap.gs.model.user;

import br.com.fiap.gs.enums.CropType;

import java.util.UUID;

public class Property {
    private UUID id;
    private UUID idFarmer;
    private String farmName;
    private CropType mainPlantation;
    private double latitude;
    private double longitude;
    private double sizeInHectares;
    private String state;

    public Property(UUID idFarmer, String farmName, double latitude, double longitude) {
        this.id = UUID.randomUUID();
        this.idFarmer = idFarmer;
        this.farmName = farmName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Property(UUID idProperty, UUID idFarmer, String farmName, double latitude, double longitude) {
        this.id = idProperty;
        this.idFarmer = idFarmer;
        this.farmName = farmName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdFarmer() {
        return idFarmer;
    }

    public void setIdFarmer(UUID idFarmer) {
        this.idFarmer = idFarmer;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public CropType getMainPlantation() {
        return mainPlantation;
    }

    public void setMainPlantation(CropType mainPlantation) {
        this.mainPlantation = mainPlantation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSizeInHectares() {
        return sizeInHectares;
    }

    public void setSizeInHectares(double sizeInHectares) {
        this.sizeInHectares = sizeInHectares;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Nome da propriedade: " + this.farmName;
    }
}
