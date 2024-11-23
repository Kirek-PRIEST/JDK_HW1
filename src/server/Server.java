package server;

import client.ClientView;
import client.ClientWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static boolean serverState = false;

    Repository repo;
    ServerView serverWindow;
    List<ClientView>usersList;

    public Server(ServerView serverWindow) {
        this.serverWindow = serverWindow;
        usersList = new ArrayList<>();
        repo = new TextRepo();
    }
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
                return serverState;
            } else {
                serverState = false;
                statusTextChanging("Down");
                serverLog(serverStatus);


                return serverState;
            }
        }
        return serverState;
    } //  включение/выключение сервера

    public Server getServer() {
        return this;
    }

    public boolean getStatus() {
        return serverState;
    }
    public List<ClientView> getUsersList(){
        return usersList;
    }
    public boolean connectUser (ClientWindow clientWindow){
        if (!serverState){
            return false;
        }else {
            usersList.add(clientWindow);
            return true;
        }
    }
    public void disconnectUser(ClientWindow clientWindow){
        usersList.remove(clientWindow);
    }

    public void loggingChat(String massage, String userName) {
        repo.loggingChat(massage, userName);
    }

    public String getChat(String userName) {
        return repo.getChatLog(userName);
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
