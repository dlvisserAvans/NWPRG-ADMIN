import client.AdminClient;
import javafx.application.Application;

public class ClientMain {

    public static void main(String[] args) {

//        AdminClient client = new AdminClient();
        AdminClient client = new AdminClient("localhost", 10000);

//        Application.launch(AdminClient.class);
        client.connect();

    }
}
