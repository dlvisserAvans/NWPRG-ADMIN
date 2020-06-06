package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerClient implements Runnable {

    private Socket socket;
    private AdminServer server;
    private DataOutputStream out;
    private DataInputStream in;
    private String name;

    public ServerClient ( Socket socket, AdminServer server ) {
        this.socket = socket;
        this.server = server;
    }

    public void writeUTF ( String text ) {
        System.out.println("Got message for client");
        try {
            this.out.writeUTF(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.in  = new DataInputStream( this.socket.getInputStream() );
            this.out = new DataOutputStream( this.socket.getOutputStream() );

            out.writeUTF("Avans ChatServer 1.2.3.4");

            this.name = in.readUTF();
            System.out.println("#### " + this.name + " joined the chat!");
            this.server.sendToAllClients("#### " + this.name + " joined the chat!");

            String message = "";
            while ( !message.equals("stop") ) {
                message = in.readUTF();
                out.writeUTF(message);
                System.out.println("avans.ti.chat.client.Client send: " + message);
                this.server.sendToAllClients("(" + this.name + "): " + message);
            }

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
