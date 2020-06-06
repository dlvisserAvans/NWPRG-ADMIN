import avans.chat.client.AdminClient;

public class ClientMain {

    public static void main(String[] args) {

        avans.chat.client.AdminClient client = new AdminClient("localhost", 10000);

        client.connect();
    }
}
