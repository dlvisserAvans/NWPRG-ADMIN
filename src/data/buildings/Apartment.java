package data.buildings;

public class Apartment extends Building {

    public Apartment(String zip, String adres, String city, int price) {
        super(zip, adres, city, BuildingType.APARTMENT, price);
    }
}
