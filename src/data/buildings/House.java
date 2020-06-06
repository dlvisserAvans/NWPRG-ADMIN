package data.buildings;

public class House extends Building {

    public House(String zip, String adres, String city, int price) {
        super(zip, adres, city, BuildingType.HOUSE, price);
    }


}
