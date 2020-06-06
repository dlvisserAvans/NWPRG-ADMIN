package client;

import cmdGUI.CMDGUI;
import cmdGUI.MenuItem.MenuItem;
import data.user.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminClient {

    private Socket socket;
    private String host;
    private int port;
    private boolean loggedIn = false;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private CMDGUI cmdgui;

    public AdminClient( String host, int port ) {
        this.host = host;
        this.port = port;
        this.cmdgui = new CMDGUI();
    }

    public boolean connect () {
        try {
            this.socket = new Socket(this.host, this.port);

            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());


            Scanner scanner = new Scanner( System.in );

            //Step 0: Show server details.
            String server = dataInputStream.readUTF();
            System.out.println(server);

            //Step 1: Log into server //TODO: Errors when user is not known.
            while (!loggedIn) {
                System.out.print("What is your username: ");
                dataOutputStream.writeUTF(scanner.nextLine());
                System.out.print("What is your password: ");
                dataOutputStream.writeUTF(scanner.nextLine());
                loggedIn = dataInputStream.readBoolean();
                System.out.println(dataInputStream.readUTF());
            }

            new Thread ( () -> {
                while ( true ) {
                    try {
                        System.out.println(dataInputStream.readUTF());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //Step 2: Let the user choose a option.
            String menuOption = "";
            while ( !menuOption.equals("quit" )) {

                    for (MenuItem menuItem : cmdgui.getMenuItems()) {
                        System.out.println(menuItem.getMenuName() + " - " + menuItem.getDescription());
                    }
                    System.out.println("Choose a menu option: ");
                    menuOption = scanner.nextLine();

                if (menuOption.equals(cmdgui.getMenuItems().get(0).getMenuName())){ //Option 1. UserAdd
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println("Who do you want to add?");
                    String name = scanner.nextLine();
                    System.out.println("Enter a passcode");
                    String passcode = scanner.nextLine();
                    objectOutputStream.writeObject(new User(name,passcode));
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(1).getMenuName())){ //Option 2. UserDel
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println(dataInputStream.readUTF());
                    String user = scanner.nextLine();
                    dataOutputStream.writeUTF(user);
                    System.out.println(dataInputStream.readUTF());
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(2).getMenuName())){ //Option 3. UserShow
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    ArrayList<User> userArrayList = (ArrayList<User>) objectInputStream.readObject(); //TODO: invalid type code: 00 onderzoeken
                    System.out.println("All the user accounts: ");
                    for (User user : userArrayList){
                        System.out.println(user.getLoginname());
                    }
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                }
//                MenuItem selectedMenuItem = null;
//                while (selectedMenuItem==null) {
//                    selectedMenuItem = cmdgui.getMenuItemByName(cmdgui.getMenuItems(), scanner.nextLine());
//                    if (selectedMenuItem == null) {
//                        System.out.println("Invalid command");
//                    } else {
//                        out.writeUTF(selectedMenuItem.toString());
//                        selectedMenuItem.Action();
//                    }
//                }


                dataOutputStream.writeUTF(menuOption);
            }

            this.socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());
            return false;
        }

        return true;
    }

    public void menuUserAdministrationChosen(){

        System.out.println("What action do you want to perform?");
    }

}
