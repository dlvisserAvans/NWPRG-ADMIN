package data;

import data.buildings.*;
import data.user.User;

import java.util.ArrayList;

public class DataBase {

    ArrayList<Building> buildings;
    ArrayList<User> users;

    public DataBase() {
        this.buildings = setupBuildingList();
        this.users = setupUserList();
    }

    public ArrayList<Building> setupBuildingList(){
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new House("21601","4483 Woodhill Avenue", "Easton"));
        buildings.add(new House("44646","3878 Rainbow Drive", "Perry Heights"));
        buildings.add(new Condo("77002","3424 Monroe Street", "Houston"));
        buildings.add(new Condo("48933","152 Ben Street", "Lansing"));
        buildings.add(new Apartment("49779","5012 Hart Ridge Road", "Rogers City"));
        buildings.add(new Apartment("75159","4164  Wilson Avenue", "Seagoville"));
        buildings.add(new Apartment("48075","4985  Mahlon Street", "Southfield"));

        return buildings;
    }

    public ArrayList<User> setupUserList(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Dave","1234"));
        users.add(new User("JKB","1234"));

        return users;
    }

    public boolean checkUserRegisterd(String userName, String password){
        for (User user : users){
            if (user.getLoginname().equals(userName)){
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public void registerNewUser(String userName, String password){
        this.users.add(new User(userName,password));
    }

    public void addNewBuilding(String zipcode, String address, String City, BuildingType buildingType){
        if (buildingType.equals(BuildingType.HOUSE)){
            this.buildings.add(new House(zipcode,address,City));
        }
        if (buildingType.equals(BuildingType.APARTMENT)){
            this.buildings.add(new Apartment(zipcode,address,City));
        }
        if (buildingType.equals(BuildingType.CONDO)){
            this.buildings.add(new Condo(zipcode,address,City));
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
