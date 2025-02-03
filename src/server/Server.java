package server;

import client.ClientView;
import client.ClientWindow;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static boolean serverState = false;

    Repository repo;
    ServerWindow serverWindow;
    List<ClientView>usersList;

    public Server(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        usersList = new ArrayList<>();
        repo = new TextRepo();
    }

    //  включение/выключение сервера
    public boolean statusServerChanging(boolean state, String serverStatus) {
        if (state == serverState) {
            if (serverState == true) {
                notification("Сервер уже включен");
            } else {
                notification("Сервер уже выключен");
            }
        } else {
            if (!serverState) {
                serverState = true;
                statusTextChanging("Up");
                serverLog(serverStatus);
                serverWindow.printToChat(repo.getChatLog());
                return serverState;
            } else {
                serverState = false;
                statusTextChanging("Down");
                serverLog(serverStatus);
                serverWindow.clearChat();


                return serverState;
            }
        }
        return serverState;
    }

    public Server getServer() {
        return this;
    }

    public boolean getStatus() {
        return serverState;
    }
    public List<ClientView> getUsersList(){
        return usersList;
    }

    //Подключение пользователя к серверу путём добавление его в список пользователей
    public boolean connectUser (ClientWindow clientWindow){
        if (!serverState){
            return false;
        }else {
            usersList.add(clientWindow);
            String message =  LocalDate.now() + "/" +
                    LocalTime.now()
                            .truncatedTo(ChronoUnit.SECONDS)
                            .format(DateTimeFormatter.ISO_LOCAL_TIME) +
                    ": Пользователь " + clientWindow.getUserName() + " подключился\n";
            serverWindow.printToChat(message);
            return true;
        }
    }
    //отключение пользователя через удаление его из списка пользователей.
    public void disconnectUser(ClientWindow clientWindow){
        String message =  LocalDate.now() + "/" +
                LocalTime.now()
                        .truncatedTo(ChronoUnit.SECONDS)
                        .format(DateTimeFormatter.ISO_LOCAL_TIME) +
                ": Пользователь " + clientWindow.getUserName() + " покинул чат\n";
        usersList.remove(clientWindow);
        loggingChat(message, clientWindow.getUserName());
    }

    //логирование чата
    public void loggingChat(String massage, String userName) {
        repo.loggingChat(massage, userName);
        serverWindow.printToChat(massage);

    }

    //Возвращает полученное сообщение и его в лог
    public String sendingMassage(String massage, String userName){
        loggingChat(massage, userName);
        return massage;
    }

    //получение лога чата пользователя
    public String getChat() {
        return repo.getChatLog();
    }


    private void statusTextChanging(String text) {

        if (!serverState) {
            serverWindow.setStatusText(text);
        } else {
            serverWindow.setStatusText(text);
        }
    }
    private void notification(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public void serverLog(String serverStatus) {
        repo.loggingServer(serverStatus);
    }

}
