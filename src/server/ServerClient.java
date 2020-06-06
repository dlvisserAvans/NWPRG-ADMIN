package server;

import cmdGUI.CMDGUI;
import data.DataBase;
import data.user.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClient implements Runnable {

    private Socket socket;
    private AdminServer server;
    private DataBase dataBase;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    private String password;
    private Boolean loggedIn = false;
    private CMDGUI cmdgui;

    public ServerClient (Socket socket, AdminServer server, DataBase dataBase) {
        this.socket = socket;
        this.server = server;
        this.dataBase = dataBase;
        this.cmdgui = new CMDGUI();
    }

    public void writeUTF ( String text ) {
        System.out.println("Got message for client");
        try {
            this.dataOutputStream.writeUTF(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());


            //Step 0: Show server details.
            dataOutputStream.writeUTF("House administration Server 0.1");

            //Step 1: Read the given user account. TODO: Errors when user is not known.
            while (!loggedIn) {
                this.username = dataInputStream.readUTF();
                this.password = dataInputStream.readUTF();
                String loginmessage = "User unknown";
                if (this.dataBase.checkUserRegisterd(this.username, this.password)) {
                    loggedIn = true;
                    dataOutputStream.writeBoolean(loggedIn);
                    loginmessage = "#### " + this.username + " has logged in ! #####";
                    System.out.println(loginmessage);
                }
                dataOutputStream.writeUTF(loginmessage);
            }
//            this.server.sendToAllClients("#### " + this.name + " joined the chat!");

            //Step 2: Let the user choose a menu option.
            String menuOption = "";
            while ( !menuOption.equals("quit") ) {
                menuOption = dataInputStream.readUTF(); //Step 2.1
                System.out.println(this.username + " chose option: " + menuOption);

                if (menuOption.equals(cmdgui.getMenuItems().get(0).getMenuName())){ //Option 1. UserAdd
                    User user = (User) objectInputStream.readObject();
                    dataBase.getUsers().add(user);
                    System.out.println("User: " + user.getLoginname() + " was added by: " + this.username);
                    menuOption = dataInputStream.readUTF();
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(1).getMenuName())){//Option 2. UserDel
                    dataOutputStream.writeUTF("Which user do you want to delete? Tip: first use command 'UserShow'");
                    String user = dataInputStream.readUTF();
                    for (User user1 : dataBase.getUsers()){
                        if (user1.getLoginname().equals(user));
                        dataBase.getUsers().remove(user1);
                    }
                    dataOutputStream.writeUTF("User: " + user + " Deleted by: " + this.username);
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(2).getMenuName())){ //Option 3. UserShow
                    System.out.println("Sending userdata from database to: " + this.username);
                    ArrayList<User> userArrayList = dataBase.getUsers();
                    objectOutputStream.writeObject(userArrayList);
                }
                objectOutputStream.flush();
                dataOutputStream.flush();
//                menuOption = dataInputStream.readUTF();
            }

            this.socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
