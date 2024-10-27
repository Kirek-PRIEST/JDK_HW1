import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 250;
    private static final int WINDOW_WEIGHT = 400;
    private static final int WINDOW_POSX = 300;
    private static final int WINDOW_POSY = 300;


    ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WEIGHT, WINDOW_HEIGHT);
        setTitle("Клиент Чата");
        setResizable(true);

        JPanel dataPanel = connectingPanel();
        add(dataPanel, BorderLayout.NORTH);


        JTextField chat = new JTextField();
        add(chat, BorderLayout.CENTER);

        JPanel massagePanel = messagePanel();
        add(massagePanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    private static JPanel connectingPanel() {
        JTextField ipAddress = new JTextField();
        JTextField port = new JTextField();
        JTextField userName = new JTextField();
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
        JTextArea massageArea = new JTextArea();
        JButton btnSend = new JButton("Отправить");

        JPanel dataPanel = new JPanel(new GridLayout(1, 2));
        dataPanel.add(massageArea);
        dataPanel.add(btnSend);
        return dataPanel;
    }

}
