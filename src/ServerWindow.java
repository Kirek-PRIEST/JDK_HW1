import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 250;
    private static final int WINDOW_WEIGHT = 400;
    private static final int WINDOW_POSX = 300;
    private static final int WINDOW_POSY = 300;
    private static final String STATUS_TEXT = "Статус: ";
    private static boolean serverState = false;

    JButton btnStart = new JButton("Включить сервер");
    JButton btnStop = new JButton("Выключить сервер");
    JTextArea text = new JTextArea();

    ServerWindow() {
        loggingServer("Сервер включен");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (serverState = true) {
                    serverState = false;
                    statusTextChanging();
                    loggingServer(null);
                }
                loggingServer("Сервер выключен");

                System.exit(0);
            }
        });
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WEIGHT, WINDOW_HEIGHT);
        setTitle("Сервер Чата");
        setResizable(false);

        JPanel panBottom = new JPanel(new GridLayout(1, 2));

        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom);
        statusTextChanging();
        add(text, BorderLayout.SOUTH);
        setVisible(true);


        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusServerChanging(true);
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusServerChanging(false);
            }
        });

    }

    private void statusServerChanging(boolean state) {
        if (state == serverState) {
            if (serverState == true) {
                notification("Сервер уже включен");
            } else {
                notification("Сервер сервер уже выключен");
            }
        } else {
            if (!serverState) {
                serverState = true;
                statusTextChanging();
                loggingServer(null);
            } else {
                serverState = false;
                statusTextChanging();
                loggingServer(null);
            }
        }
    }

    private void statusTextChanging() {
        if (!serverState) {
            text.setText(STATUS_TEXT + "Down");
        } else {
            text.setText(STATUS_TEXT + "Up");
        }
    }

    private void loggingServer(String stat) {
        try (FileWriter writer = new FileWriter("ServerLog.txt", true)) {
            if (stat != null) {
                writer.write(new Date() + ": " + stat + "\n");
            } else {
                writer.write(new Date() + ": " + text.getText() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void notification(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
    public static boolean getStatus(){
        return serverState;
    }


}
