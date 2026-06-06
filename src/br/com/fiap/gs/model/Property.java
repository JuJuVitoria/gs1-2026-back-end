package br.com.fiap.gs.model;

public class Property {
    private long id;
    private long idFarmer;
    private String farmName;
    //depois alterar para o enum CropType
    private String mainPlantation;
    private String latitude;
    private String longitude;
    private double sizeInHectares;
    private String state;

    public Property(long idFarmer, String farmName, String latitude, String longitude) {
        this.idFarmer = idFarmer;
        this.farmName = farmName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdFarmer() {
        return idFarmer;
    }

    public void setIdFarmer(long idFarmer) {
        this.idFarmer = idFarmer;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getMainPlantation() {
        return mainPlantation;
    }

    public void setMainPlantation(String mainPlantation) {
        this.mainPlantation = mainPlantation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
}
