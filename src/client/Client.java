package client;

import server.Server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
    //Отправка сообщения. Вывод отправленного сообщения в чат
    public void massageSending(String message, String userName) {
        if (server.getStatus()) {
            message = userName + " > " + LocalDate.now() + "/" +
                    LocalTime.now()
                    .truncatedTo(ChronoUnit.SECONDS)
                    .format(DateTimeFormatter.ISO_LOCAL_TIME) +
                    ": " + message + "\n";
            printText(server.sendingMassage(message, userName));
        } else {
            printText("Вы отключены от сервера");
            view.changeVisibleConnection(false);
        }
    }

    public String getName() {
        return name;
    }


    public void connectingToServer(String name) {
        this.name = name;
        if (server.connectUser(view)) {

            printText("Вы подключились к серверу \n");
            connected = true;
            String log = server.getChat();
            if (log != null) {
                printText(log);
            }
            view.changeVisibleConnection(true);
        } else printText("Не удалось подключиться к серверу \n");
    }

    public void disconnectingFromServer() {
        server.disconnectUser(view);
        view.changeVisibleConnection(false);
        printText("Вы отключились от сервера\n");
    }


}
