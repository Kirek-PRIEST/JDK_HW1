package server;


import java.io.*;
import java.util.Date;

public class TextRepo implements Repository {


    // запись лога сервера
    public void loggingServer(String serverStatus) {
        try (FileWriter writer = new FileWriter("ServerLog.txt", true)) {
                writer.write(new Date() + ": " + serverStatus + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // запись лога чата
    public void loggingChat(String massage, String userName) {
        try (FileWriter writer = new FileWriter("chatLog.txt", true)) {
            writer.write(massage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // создание файла лога для нового пользователя
    private void createNewFile(String userName){
        try {
            File file = new File("chatLog.txt");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }
    }

    // выдаёт лог чата пользователя
    public String getChatLog() {
        File file = new File( "chatLog.txt");
        if (file.exists()) {
            String text;
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader("chatLog.txt"))) {
                while ((text = reader.readLine()) != null) {
                    result.append(text).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (result + "\n");
        }else {
            createNewFile("chatLog.txt");
            return null;
        }
    }

}
