package client;

import client.Client;
import client.ClientView;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ClientWindow extends JFrame implements ClientView {
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WEIGHT = 400;
    private static final int WINDOW_POSX = 700;
    private static final int WINDOW_POSY = 300;

    private static int positionShift = 0;


    private JTextField massageArea = new JTextField();
    private final JButton btnSend = new JButton("Отправить");
    private JTextField ipAddress = new JTextField("127.0.0.1");
    private JTextField port = new JTextField("8189");
    private JTextField userName = new JTextField("max");
    private JTextArea chat = new JTextArea();
    private JButton btnLogin = new JButton("Login");
    private JButton btnLogOut = new JButton("Logout");
    private JPanel hideDataPanel;
    private JPanel dataPanel;
    private Server server;
    private Client client;
    private String name;


    public ClientWindow(Server server) {

        this.server = server;
        client = new Client(this, server.getServer());

        windowCreation();
        connectingPanel();
        messagePanel();
        chatField();

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.massageSending(massageArea.getText(), userName.getText());
            }
        });

        setVisible(true);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });

    }

    //создаёт само окно без наполнения
    private void windowCreation() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(WINDOW_POSX + positionShift, WINDOW_POSY + positionShift);
        setSize(WINDOW_WEIGHT, WINDOW_HEIGHT);
        setTitle("Клиент Чата");
        setResizable(true);
        positionShift += 30;

    }

    // создаёт и добавляет поля для подключения к серверу
    private void connectingPanel() {
        dataPanel = new JPanel(new GridLayout(3, 3));
        hideDataPanel = new JPanel();
        JTextArea ipArea = new JTextArea("IP");
        JTextArea portArea = new JTextArea("Port");
        JTextArea userNameArea = new JTextArea("Username");
        ipArea.setEditable(false);
        portArea.setEditable(false);
        userNameArea.setEditable(false);
        btnLogOut.setEnabled(false);
        dataPanel.add(ipArea);
        dataPanel.add(portArea);
        dataPanel.add(userNameArea);
        dataPanel.add(ipAddress);
        dataPanel.add(port);
        dataPanel.add(userName);
        dataPanel.add(btnLogin);
        dataPanel.add(btnLogOut);
        this.add(dataPanel, BorderLayout.NORTH);

    }

    // создаёт и добавляет поле для отправки сообщения
    private void messagePanel() {
        JPanel dataPanel = new JPanel(new GridLayout(1, 2));
        dataPanel.add(massageArea);
        dataPanel.add(btnSend);
        btnSend.setEnabled(false);
        this.add(dataPanel, BorderLayout.SOUTH);
    }

    // добавление поля чата
    private void chatField() {
        chat.setEditable(false);
        this.add(new JScrollPane(chat));
    }



    // меняет доступность кнопок при подключении или отключении сервера
    public void changeVisibleConnection(boolean action) {
        if ((action)) {
            btnLogin.setEnabled(false);
            btnLogOut.setEnabled(true);
            btnSend.setEnabled(true);
        } else {
            btnLogOut.setEnabled(false);
            btnLogin.setEnabled(true);
            btnSend.setEnabled(false);
        }
    }
    public String getUserName(){
        return userName.getText();
    }

    @Override
    public void massageSanding(String message) {
        chat.append(message);
        massageArea.setText("");
    }

    @Override
    public void logIn() {
        chat.setText("");
        client.connectingToServer(userName.getText());

    }

    @Override
    public void logOut() {
        client.disconnectingFromServer();
        chat.setText("");
        chat.append("Вы вышли из чата");
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.logOut();
        }
    }
}
