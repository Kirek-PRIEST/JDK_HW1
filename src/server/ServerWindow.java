package server;

import server.Server;
import server.ServerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerWindow extends JFrame implements ServerView {
    private static final int WINDOW_HEIGHT = 250;
    private static final int WINDOW_WEIGHT = 400;
    private static final int WINDOW_POSX = 300;
    private static final int WINDOW_POSY = 300;
    private final String STATUS_TEXT = "Статус: ";
    private boolean serverState = false;

    private JButton btnStart = new JButton("Включить сервер");
    private JButton btnStop = new JButton("Выключить сервер");
    private JTextArea statusText = new JTextArea();

    Server server;

    public ServerWindow() {
        server = new Server(this);
        server.serverLog("Сервер включен");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (serverState = true) {
                    serverState = false;
                    server.serverLog("Сервер выключен");
                } else {
                    server.serverLog("Сервер выключен");
                }
                System.exit(0);
            }
        });

        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WEIGHT, WINDOW_HEIGHT);
        setTitle("Сервер Чата");
        setResizable(false);

        JPanel panBottom = new JPanel(new GridLayout(1, 2));

        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom);
        setStatusText("Down");
        add(statusText, BorderLayout.SOUTH);
        setVisible(true);


        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

    }

    public void setStatusText(String text) {
        statusText.setText(STATUS_TEXT + text);
    }

    @Override
    public void startServer() {
        setStatusText("Up");
        server.statusServerChanging(true, statusText.getText());
    }

    @Override
    public void stopServer() {
        setStatusText("Down");
        server.statusServerChanging(false, statusText.getText());
    }


}






