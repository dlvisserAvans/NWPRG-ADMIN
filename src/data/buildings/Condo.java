package data.buildings;

public class Condo extends Building {

    public Condo(String zip, String adres, String city, int price) {
        super(zip, adres, city, BuildingType.CONDO, price);
    }
}
