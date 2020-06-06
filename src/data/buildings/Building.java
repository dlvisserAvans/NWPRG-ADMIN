package data.buildings;

import java.io.Serializable;
import java.util.PriorityQueue;

public abstract class Building implements Serializable {

    String Zip;
    String Adres;
    String City;
    BuildingType buildingType;
    int Price;

    public Building(String zip, String adres, String city, BuildingType buildingType, int price) {
        Zip = zip;
        Adres = adres;
        City = city;
        this.buildingType = buildingType;
        Price = price;
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price = price;
    }

    public String toString() {
        return "Building: " + buildingType + ", " + Zip + ", " + Adres + ", " + City + ", " + Price;
    }
}
