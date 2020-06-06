import client.AdminClient;

public class ClientMain {

    public static void main(String[] args) {

        AdminClient client = new AdminClient("localhost", 10000);

        client.connect();
    }
}
