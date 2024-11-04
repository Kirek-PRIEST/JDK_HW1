import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;

public class ClientWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 250;
    private static final int WINDOW_WEIGHT = 400;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 500;

    private static JTextField massageArea = new JTextField();
    private static final JButton btnSend = new JButton("Отправить");
    private static JTextField ipAddress = new JTextField();
    private static JTextField port = new JTextField();
    private static JTextField userName = new JTextField("max");
    private static JTextArea chat = new JTextArea();
    private static JButton btnLogin = new JButton("Login");
    private String chatName = userName.getText() + ".txt";
    private ServerWindow server;


    ClientWindow(ServerWindow server) {
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WEIGHT, WINDOW_HEIGHT);
        setTitle("Клиент Чата");
        setResizable(true);

        JPanel dataPanel = connectingPanel();
        add(dataPanel, BorderLayout.NORTH);

        chat.setEditable(false);
        add(chat, BorderLayout.CENTER);


        JPanel massagePanel = messagePanel();
        add(massagePanel, BorderLayout.SOUTH);
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                massageSending();
            }
        });

        setVisible(true);
        //chatView();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });

    }

    private void logIn() {
        chatName = userName.getText() + ".txt";
        if (!server.getStatus()) {
            chat.setText("Сервер не подключен");
        }else {
            String text;
            chat.setText("");
            try (BufferedReader reader = new BufferedReader(new FileReader(chatName))) {
                while ((text = reader.readLine()) != null) {
                    System.out.println(text + "\n");
                    chat.append(text + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static JPanel connectingPanel() {
        JTextArea ipArea = new JTextArea("IP");
        JTextArea portArea = new JTextArea("Port");
        JTextArea userNameArea = new JTextArea("Username");
        JPanel dataPanel = new JPanel(new GridLayout(3, 3));
        ipArea.setEditable(false);
        portArea.setEditable(false);
        userNameArea.setEditable(false);
        dataPanel.add(ipArea);
        dataPanel.add(portArea);
        dataPanel.add(userNameArea);
        dataPanel.add(ipAddress);
        dataPanel.add(port);
        dataPanel.add(userName);
        dataPanel.add(btnLogin);
        return dataPanel;
    }

    private static JPanel messagePanel() {

        JPanel dataPanel = new JPanel(new GridLayout(1, 2));
        dataPanel.add(massageArea);
        dataPanel.add(btnSend);
        return dataPanel;

    }

    private void massageSending() {
        loggingChat();
        chat.append(userName.getText() + " > " + new Date() + ": " + massageArea.getText() + "\n");
        massageArea.setText("");

    }


    private void loggingChat() {
        try (FileWriter writer = new FileWriter(chatName, true)) {
            writer.write(userName.getText() + " > " + new Date() + ": " + massageArea.getText() + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void chatView() {
        while (!server.getStatus()) {
            chat.setText("Сервер не подключен");
        }
        String text;
        try (BufferedReader reader = new BufferedReader(new FileReader(chatName))) {
            while ((text = reader.readLine()) != null) {
                System.out.println(text + "\n");
                chat.append(text + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
