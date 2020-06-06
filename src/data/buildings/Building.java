package data.buildings;

import java.io.Serializable;

public abstract class Building implements Serializable {

    String Zip;
    String Adres;
    String City;
    BuildingType buildingType;

    public Building(String zip, String adres, String city, BuildingType buildingType) {
        Zip = zip;
        Adres = adres;
        City = city;
        this.buildingType = buildingType;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getAdres() {
        return Adres;
    }

    public void setAdres(String adres) {
        Adres = adres;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
