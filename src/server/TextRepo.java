package server;

import server.Repository;

import java.io.*;
import java.util.Date;

public class TextRepo implements Repository {



    public void loggingServer(String serverStatus) {
        try (FileWriter writer = new FileWriter("ServerLog.txt", true)) {
                writer.write(new Date() + ": " + serverStatus + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // запись лога сервера
    public void loggingChat(String massage, String userName) {
        try (FileWriter writer = new FileWriter(userName + ".txt", true)) {
            writer.write(massage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    } // запись лога чата
    private void createNewFile(String userName){
        try {
            File file = new File(userName + ".txt");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }
    } // создание файла лога для нового пользователя

    public String getChatLog(String userName) {
        File file = new File(userName + ".txt");
        if (file.exists()) {
            String text;
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(userName + ".txt"))) {
                while ((text = reader.readLine()) != null) {
                    result.append(text).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return (result + "\n");
        }else {
            createNewFile(userName);
            return null;
        }
    } // выдаёт лог чата пользователя

}
