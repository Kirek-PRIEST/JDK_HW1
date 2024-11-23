package client;

import server.Server;

import java.util.Date;

public class Client {

    private Server server;
    private ClientWindow view;
    private boolean connected;
    private String name;

    public Client(ClientWindow view, Server server) {
        this.view = view;
        this.server = server;
        this.connected = server.getStatus();
    }

    private void printText(String text) {
        view.massageSanding(text);
    }

    public void massageSending(String massage, String userName) {
        if(server.getStatus()) {
            massage = userName + " > " + new Date() + ": " + massage + "\n";
            printText(massage);
            server.loggingChat(massage, userName);
        } else {
            printText("Вы отключены от сервера");
            view.changeVisibleConnection(false);
        }
    }
public String getName(){
        return name;
}
    public void serverAnswer(String text) {
        printText(text);
    }

    public void connectingToServer(String name) {
        this.name = name;
        if (server.connectUser(view)) {
            printText("Вы подключились к серверу \n");
            connected = true;
            String log = server.getChat(name);
            if (log != null){
                printText(log);
            }
            view.changeVisibleConnection(true);
        }else printText("Не удалось подключиться к серверу \n");
    }
    public void disconnectingFromServer(){
        server.disconnectUser(view);
        view.changeVisibleConnection(false);
        printText("Вы отклюились от сервера\n");
    }


}
