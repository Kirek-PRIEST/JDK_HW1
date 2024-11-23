import client.ClientWindow;
import server.Server;
import server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        Server server = new Server(serverWindow);

        new ClientWindow(server);
    }
}