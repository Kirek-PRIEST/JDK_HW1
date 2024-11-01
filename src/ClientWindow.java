import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;

public class ClientWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 250;
    private static final int WINDOW_WEIGHT = 400;
    private static final int WINDOW_POSX = 300;
    private static final int WINDOW_POSY = 300;

    private static JTextField massageArea = new JTextField();
    private static final JButton btnSend = new JButton("Отправить");
    private static JTextField ipAddress = new JTextField();
    private static JTextField port = new JTextField();
    private static JTextField userName = new JTextField("max");
    private static JTextArea chat = new JTextArea();
    private String chatName = userName.getText() + ".txt";


    ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WEIGHT, WINDOW_HEIGHT);
        setTitle("Клиент Чата");
        setResizable(true);

        JPanel dataPanel = connectingPanel();
        add(dataPanel, BorderLayout.NORTH);


        add(chat, BorderLayout.CENTER);
        chatView();

        JPanel massagePanel = messagePanel();
        add(massagePanel, BorderLayout.SOUTH);
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                massageSending();
            }
        });

        setVisible(true);

    }

    private static JPanel connectingPanel() {
        JTextArea ipArea = new JTextArea("IP");
        JTextArea portArea = new JTextArea("Port");
        JTextArea userNameArea = new JTextArea("Username");
        JButton btnLogin = new JButton("Login");
        JPanel dataPanel = new JPanel(new GridLayout(3, 3));
        dataPanel.add(ipArea);
        dataPanel.add(portArea);
        dataPanel.add(userNameArea);
        dataPanel.add(ipAddress);
        dataPanel.add(port);
        dataPanel.add(userName);
        dataPanel.add(btnLogin);
        return dataPanel;
    }
    private static JPanel messagePanel(){

        JPanel dataPanel = new JPanel(new GridLayout(1, 2));
        dataPanel.add(massageArea);
        dataPanel.add(btnSend);
        return dataPanel;

    }

    private void massageSending(){
        loggingChat();
        chat.append(userName.getText() + " > " + new Date() + ": " + massageArea.getText() + "\n");
        massageArea.setText("");

    }


    private void loggingChat(){
        try (FileWriter writer = new FileWriter(chatName, true)) {
                writer.write(userName.getText() + " > " + new Date() + ": " + massageArea.getText() + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void chatView(){
        while (!ServerWindow.getStatus) {

            chatView();

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
